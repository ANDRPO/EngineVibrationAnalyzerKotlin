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
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.feature.SingleLiveEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.timer
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@ExperimentalCoroutinesApi
class DiagnosticViewModel(
    private val interactorSensor: InteractorSensor,
    private val observeConfigurationCarUseCase: ObserveConfigurationCarUseCase,
    private val sendDiagnosticAndEventsDataUseCase: SendDiagnosticAndEventsDataUseCase
) : ViewModel() {

    private val _states = MutableLiveData<StateDiagnostic>()
    val states: LiveData<StateDiagnostic> = _states

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> = _progress

    private val _titleNotify = MutableLiveData<String>()
    val titleNotify: LiveData<String> = _titleNotify

    val message = SingleLiveEvent<String>()

    private val listEvents: MutableList<EventModel> = mutableListOf()

    private val procedureReadEvents = viewModelScope.launch {
        interactorSensor.streamEvent().collect {
            listEvents.add(it)
        }
    }

    fun launchDiagnostic() = viewModelScope.launch {
        interactorSensor.startSensor()

        procedureReadEvents.start()
        delay(7000)

    }

    suspend fun myCoroutineTimer() = suspendCoroutine<Unit> {

    }

    fun cancelDiagnostic() {
        interactorSensor.stopSensor()

        procedureReadEvents.cancel()

        _progress.postValue(0)
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

    fun applyDefaultState() {
        _titleNotify.postValue("До начала диагностики:")
        //_progress.postValue("00:03")
    }

    fun applyPreStart() {

    }

    fun applyStart() {
        _titleNotify.postValue("До окончания диагнстики:")
       // _progress.postValue("00:07")
    }

    fun applySuccess() {
        _titleNotify.postValue("Тестирование завершено")
    }

    fun applyError() {
        _titleNotify.postValue("Произошла ошибка при проведении диагностики")

    }
}