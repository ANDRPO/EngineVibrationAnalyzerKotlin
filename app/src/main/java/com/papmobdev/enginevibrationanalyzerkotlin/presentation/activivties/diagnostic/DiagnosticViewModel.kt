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

    private val _statesView = MutableLiveData(StatesViewDiagnostic.DEFAULT)
    val statesView: LiveData<StatesViewDiagnostic> = _statesView

    private val observeProgressDiagnostic = viewModelScope.launch {
        interactorDiagnostic.progress.collect {
            _progress.postValue(it)
        }
    }

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

    private val _progress = MutableLiveData(0)
    val progress: LiveData<Int> = _progress

    private val _titleNotify = MutableLiveData<String>()
    val titleNotify: LiveData<String> = _titleNotify

    val message = SingleLiveEvent<String>()

    fun startDiagnostic() {
        viewModelScope.launch(Dispatchers.IO) {
            interactorDiagnostic.startDiagnostic(this@DiagnosticViewModel.viewModelScope)
        }
    }

    fun stopDiagnostic() {
        interactorDiagnostic.cancelDiagnostic()
    }

    fun applyDefault() {
        _titleNotify.postValue("Проведение диагностики")
        _progress.postValue(0)
    }

    fun applyStart() {
        _titleNotify.postValue("Диагностика началась")
        _progress.postValue(0)
    }

    fun applySuccess() {
        _titleNotify.postValue("Тестирование завершено")
    }

    fun applyError() {
        _titleNotify.postValue("Произошла ошибка при проведении диагностики")
        _progress.postValue(0)
    }

    fun applyCancel() {
        _titleNotify.postValue("Диагностика прервана. Нажмите старт, чтобы повторить")
        _progress.postValue(0)
    }
}