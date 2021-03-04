package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.diagnostic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papmobdev.domain.diagnostic.diagnosticinteractor.InteractorDiagnostic
import com.papmobdev.domain.diagnostic.diagnosticinteractor.StatesDiagnostic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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

    var job: Job = Job()

    val _stateView = MutableLiveData<StatesViewDiagnostic>()
    val stateView: LiveData<StatesViewDiagnostic> = _stateView

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> = _progress

    private val _titleNotify = MutableLiveData<String>()
    val titleNotify: LiveData<String> = _titleNotify

    private val _textControlDiagnosticButton = MutableLiveData<String>()
    val textControlDiagnosticButton: LiveData<String> = _textControlDiagnosticButton

    override fun onCleared() {
        Log.e("CLEARED", "CLEARED")
        super.onCleared()

    }

    fun startDiagnostic() {
        job = viewModelScope.launch(Dispatchers.IO) {
            interactorDiagnostic.startDiagnostic()
        }
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