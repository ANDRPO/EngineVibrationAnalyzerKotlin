package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.diagnostic

import androidx.lifecycle.*
import com.papmobdev.domain.diagnostic.diagnosticinteractor.InteractorDiagnostic
import com.papmobdev.domain.diagnostic.diagnosticinteractor.StatesDiagnostic
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.feature.SingleLiveEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.util.*

@ExperimentalCoroutinesApi
class DiagnosticViewModel(
    private val interactorDiagnostic: InteractorDiagnostic
) : ViewModel() {

    var job: Job = Job()

    private val _statesView = MutableLiveData(StatesViewDiagnostic.DEFAULT)
    val statesView: LiveData<StatesViewDiagnostic> = _statesView

    private val observeStatesDiagnostic = viewModelScope.launch {
        interactorDiagnostic.stateDiagnostic.collect {
            val stateView = when (it) {
                StatesDiagnostic.NONE -> StatesViewDiagnostic.DEFAULT
                StatesDiagnostic.START -> StatesViewDiagnostic.START
                StatesDiagnostic.SUCCESS -> StatesViewDiagnostic.SUCCESS
                StatesDiagnostic.CANCEL -> StatesViewDiagnostic.CANCEL
                StatesDiagnostic.ERROR -> StatesViewDiagnostic.ERROR
            }
            _statesView.postValue(stateView)
        }
    }

    val progress: LiveData<Int> = interactorDiagnostic.progress.asLiveData()

    private val _titleNotify = MutableLiveData<String>()
    val titleNotify: LiveData<String> = _titleNotify

    private val _textControlTestButton = MutableLiveData<String>()
    val textControlTestButton: LiveData<String> = _textControlTestButton

    val message = SingleLiveEvent<String>()

    fun startDiagnostic() {
        job = viewModelScope.launch(Dispatchers.IO) {
            interactorDiagnostic.startDiagnostic()
        }
    }

    fun stopDiagnostic() {
        interactorDiagnostic.cancelDiagnostic()
        job.cancel()
    }

    fun applyDefault() {
        _titleNotify.postValue("Проведение диагностики")
        _textControlTestButton.postValue("Старт")
        job.cancel()
    }

    fun applyStart() {
        _titleNotify.postValue("Диагностика началась")
        _textControlTestButton.postValue("Стоп")
    }

    fun applySuccess() {
        _titleNotify.postValue("Тестирование завершено")
        _textControlTestButton.postValue("Далее")
        job.cancel()
    }

    fun applyError() {
        _titleNotify.postValue("Произошла ошибка при проведении диагностики")
        _textControlTestButton.postValue("Повторить")
        job.cancel()
    }

    fun applyCancel() {
        _titleNotify.postValue("Диагностика прервана.")
        _textControlTestButton.postValue("Повторить")
        job.cancel()
    }
}