package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.diagnostic

sealed class StateDiagnostic {
    object Success : StateDiagnostic()
    object Error : StateDiagnostic()
    object Default : StateDiagnostic()
    object PreStart : StateDiagnostic()
    object Start : StateDiagnostic()

    fun invoke() = Default
}