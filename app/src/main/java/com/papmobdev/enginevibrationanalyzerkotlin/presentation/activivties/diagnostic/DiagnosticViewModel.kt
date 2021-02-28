package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.diagnostic

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papmobdev.domain.cars.models.CarConfiguration
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.ObserveConfigurationCarUseCase
import com.papmobdev.domain.diagnostic.models.DiagnosticModel
import com.papmobdev.domain.diagnostic.models.SendDiagnosticAndEventsModel
import com.papmobdev.domain.diagnostic.usecasediagnosticandeventsdata.SendDiagnosticAndEventsDataUseCase
import com.papmobdev.domain.sensor.interactor.InteractorSensor
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
class DiagnosticViewModel(
    private val interactorSensor: InteractorSensor,
    private val observeConfigurationCarUseCase: ObserveConfigurationCarUseCase,
    private val sendDiagnosticAndEventsDataUseCase: SendDiagnosticAndEventsDataUseCase
) : ViewModel() {

    private val _states = MutableLiveData<StateDiagnostic>()
    val states: LiveData<StateDiagnostic> = _states

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    private val _titleNotify = MutableLiveData<String>()
    val titleNotify: LiveData<String> = _titleNotify

    private val listEvents: MutableList<EventModel> = mutableListOf()

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
            sendDiagnosticData(listEvents)
        })

    private val procedureReadEvents = viewModelScope.launch {
        interactorSensor.streamEvent().collect {
            listEvents.add(it)
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
        listEvents.clear()
    }

    private fun sendDiagnosticData(list: List<EventModel>) {
        viewModelScope.launch {
            val modelDiagnosticAndEvents = SendDiagnosticAndEventsModel(
                getCarConfiguration().toDiagnosticModel(),
                list
            )
            sendDiagnosticAndEventsDataUseCase(modelDiagnosticAndEvents).collect { result ->
                result.onSuccess {
                    if (!it) _states.postValue(StateDiagnostic.Error)
                }
            }
        }
    }

    private suspend fun getCarConfiguration() =
        observeConfigurationCarUseCase().first().getOrNull() ?: CarConfiguration()

    private fun CarConfiguration.toDiagnosticModel() = DiagnosticModel(
        idDiagnostic = null,
        dateTime = Date(),
        fkCarMark = fkCarMark,
        fkCarModel = fkCarModel,
        fkCarGeneration = fkCarGeneration,
        fkTypeFuel = fkTypeFuel,
        engineVolume = engineVolume,
        fkVibrationSource = fkTypeSource,
        note = note
    )

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
}