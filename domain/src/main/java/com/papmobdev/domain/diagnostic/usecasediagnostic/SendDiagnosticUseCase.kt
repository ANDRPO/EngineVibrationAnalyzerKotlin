package com.papmobdev.domain.diagnostic.usecasediagnostic

import com.papmobdev.domain.FlowUseCase
import com.papmobdev.domain.diagnostic.models.DiagnosticModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface SendDiagnosticUseCase : FlowUseCase<DiagnosticModel, Boolean>