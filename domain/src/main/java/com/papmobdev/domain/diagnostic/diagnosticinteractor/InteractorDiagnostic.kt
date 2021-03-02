package com.papmobdev.domain.diagnostic.diagnosticinteractor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface InteractorDiagnostic {
    val progress: Flow<Int>
    val stateDiagnostic: StateFlow<StatesDiagnostic>

    fun startDiagnostic(scope: CoroutineScope)
    fun cancelDiagnostic()
}