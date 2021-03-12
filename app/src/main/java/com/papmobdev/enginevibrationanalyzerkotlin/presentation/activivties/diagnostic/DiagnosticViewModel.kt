package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.diagnostic

import androidx.lifecycle.*
import com.papmobdev.domain.diagnostic.diagnosticinteractor.InteractorDiagnostic
import com.papmobdev.domain.diagnostic.diagnosticinteractor.StatesDiagnostic
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.cancellation.CancellationException

@ExperimentalCoroutinesApi
class DiagnosticViewModel(
    private val interactorDiagnostic: InteractorDiagnostic
) : ViewModel() {

    init {
        viewModelScope.launch {
            interactorDiagnostic.stateDiagnostic.collect {
                _stateView.postValue(it.toViewStates())
            }
        }
        viewModelScope.launch {
            interactorDiagnostic.progress.collect {
                _progress.postValue(it)
            }
        }
    }

    private var job: Job = Job()

    private val _stateView = MutableLiveData(StatesViewDiagnostic.DEFAULT)
    val stateView: LiveData<StatesViewDiagnostic> = _stateView

    private val _progress = MutableLiveData(0)
    val progress: LiveData<Int> = _progress

    private val _titleNotify = MutableLiveData<String>()
    val titleNotify: LiveData<String> = _titleNotify

    private val _textControlDiagnosticButton = MutableLiveData<String>()
    val textControlDiagnosticButton: LiveData<String> = _textControlDiagnosticButton

    fun startDiagnostic() {
        job.cancel()
        job = (viewModelScope.launch(Dispatchers.IO) {
            interactorDiagnostic.startDiagnostic()
        })
    }

    fun stopDiagnostic() {
        viewModelScope.launch {
            interactorDiagnostic.cancelDiagnostic()
        }
        job.cancel()
    }

    fun applyDefault() {
        _titleNotify.postValue("Проведение диагностики")
        _textControlDiagnosticButton.postValue("Старт")
        _progress.postValue(0)
        job.cancel()
    }

    fun applyStart() {
        _titleNotify.postValue("Диагностика началась")
        _textControlDiagnosticButton.postValue("Стоп")
    }

    fun applySuccess() {
        _titleNotify.postValue("Тестирование завершено")
        _textControlDiagnosticButton.postValue("Далее")
        job.cancel()
    }

    fun applyError() {
        _titleNotify.postValue("Произошла ошибка при проведении диагностики")
        _textControlDiagnosticButton.postValue("Повторить")
        job.cancel()
    }

    fun applyCancel() {
        _titleNotify.postValue("Диагностика прервана.")
        _textControlDiagnosticButton.postValue("Повторить")
        job.cancel()
    }


    private fun StatesDiagnostic.toViewStates(): StatesViewDiagnostic = when (this) {
        StatesDiagnostic.NONE -> StatesViewDiagnostic.DEFAULT
        StatesDiagnostic.START -> StatesViewDiagnostic.START
        StatesDiagnostic.SUCCESS -> StatesViewDiagnostic.SUCCESS
        StatesDiagnostic.CANCEL -> StatesViewDiagnostic.CANCEL
        StatesDiagnostic.ERROR -> StatesViewDiagnostic.ERROR
    }
}