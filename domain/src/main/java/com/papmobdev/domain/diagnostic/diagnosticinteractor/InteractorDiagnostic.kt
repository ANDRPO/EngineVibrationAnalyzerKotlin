package com.papmobdev.domain.diagnostic.diagnosticinteractor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface InteractorDiagnostic {
    val progress: Flow<Int>
    val stateDiagnostic: SharedFlow<StatesDiagnostic>

    suspend fun startDiagnostic()
    suspend fun cancelDiagnostic()
}