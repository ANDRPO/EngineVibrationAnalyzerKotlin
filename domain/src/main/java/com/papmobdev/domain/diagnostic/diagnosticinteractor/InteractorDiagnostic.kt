package com.papmobdev.domain.diagnostic.diagnosticinteractor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface InteractorDiagnostic {
    val progress: Flow<Int>
    val stateDiagnostic: StateFlow<StatesDiagnostic>

    suspend fun startDiagnostic()
    fun cancelDiagnostic()
}