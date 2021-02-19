package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.diagnostic

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papmobdev.domain.diagnostic.models.DiagnosticModel
import com.papmobdev.domain.diagnostic.usecasediagnostic.SendDiagnosticUseCase
import com.papmobdev.domain.diagnostic.usecasesensorevents.SendListSensorEventsUseCase
import com.papmobdev.domain.sensor.interactorsensor.InteractorSensor
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class DiagnosticViewModel(
    private val interactorSensor: InteractorSensor,
    private val sendDiagnosticUseCase: SendDiagnosticUseCase,
    private val sendListSensorEventsUseCase: SendListSensorEventsUseCase
) : ViewModel() {
    private val _time = MutableLiveData<String>()
    private val time: LiveData<String> = _time
    private val diagnosticDownTimer = DiagnosticDownTimer(7000L,
        {
            Log.e("TIMETICK", it.toString())
        },
        {
            Log.e("TIMETICK", "FINISH")
        })

    private lateinit var diagnosticModel: DiagnosticModel
    private val list: MutableList<EventModel> = mutableListOf()

    fun startDiagnostic() {
        viewModelScope.launch {
            interactorSensor.startSensor()
            interactorSensor.streamEvent().collect {
                list.add(it)
            }


        }
        viewModelScope.launch {
            delay(7000)
            interactorSensor.stopSensor()
            Log.e("SIZELIST", list.size.toString())

        }
    }

    class DiagnosticDownTimer(
        millisInFuture: Long,
        private val funcOnTick: (millisUntilFinished: Long) -> Unit,
        private val funcOnFinish: () -> Unit
    ) : CountDownTimer(millisInFuture, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            funcOnTick(millisUntilFinished)
        }

        override fun onFinish() {
            funcOnFinish()
        }

    }
}