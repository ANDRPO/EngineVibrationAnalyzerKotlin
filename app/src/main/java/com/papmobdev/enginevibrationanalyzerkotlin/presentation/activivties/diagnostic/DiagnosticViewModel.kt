package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.diagnostic

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.ObserveConfigurationCarUseCase
import com.papmobdev.domain.diagnostic.usecasediagnosticdata.SendDiagnosticDataUseCase
import com.papmobdev.domain.diagnostic.models.DiagnosticModel
import com.papmobdev.domain.sensor.interactor.InteractorSensor
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class DiagnosticViewModel(
    private val interactorSensor: InteractorSensor,
    private val sendDiagnosticDataUseCase: SendDiagnosticDataUseCase,
) : ViewModel() {

    private val _states = MutableLiveData<StateDiagnostic>()
    val states: LiveData<StateDiagnostic> = _states

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    private val _titleNotify = MutableLiveData<String>()
    val titleNotify: LiveData<String> = _titleNotify

    private val _showMessage = MutableLiveData<String>()
    val showMessage: LiveData<String> = _showMessage

    private val list: MutableList<EventModel> = mutableListOf()

    private val preDiagnosticDownTimer = DiagnosticDownTimerProcedure(3000L,
        {
            _time.postValue("00:0" + it / 1000)
        },
        {
            _titleNotify.postValue("Секунд до окончания тестирования")

            procedureReadEvents.start()
            diagnosticDownTimer.start()
        })


    private val diagnosticDownTimer = DiagnosticDownTimerProcedure(7000L,
        {
            _time.postValue("00:0" + it / 1000)
        },
        {
            _titleNotify.postValue("Тестирование завершено")
            interactorSensor.stopSensor()
            procedureReadEvents.cancel()
            sendList(list)
        })

    private val procedureReadEvents = viewModelScope.launch {
        interactorSensor.streamEvent().collect {
            list.add(it)
        }
    }

    fun startDiagnostic() = viewModelScope.launch {
        interactorSensor.startSensor()
        preDiagnosticDownTimer.start()
    }

    fun cancelDiagnostic() {
        preDiagnosticDownTimer.cancel()
        diagnosticDownTimer.cancel()

        interactorSensor.stopSensor()

        procedureReadEvents.cancel()

        _time.postValue("00:03")
        _titleNotify.postValue("До начала диагностики:")
        list.clear()
    }

    private fun sendList(list: List<EventModel>) {
        viewModelScope.launch {
            sendDiagnosticDataUseCase.writeDiagnostic(list).collect { result ->
                result.onSuccess {
                    if (!it) _showMessage.postValue("Произоршла ошибка при записи данных")
                }
            }
        }
    }

    fun applyDefaultState() {
        _titleNotify.postValue("До начала диагностики:")
        _time.postValue("00:03")
    }

    fun applyPreStart() {

    }

    fun applyStart() {
        _titleNotify.postValue("До окончания диагнстики:")
        _time.postValue("00:07")
    }

    fun applySuccess() {
        _titleNotify.postValue("Тестирование завершено")
    }

    fun applyError() {
        _titleNotify.postValue("Произошла ошибка при проведении диагностики")
    }

    class DiagnosticDownTimerProcedure(
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