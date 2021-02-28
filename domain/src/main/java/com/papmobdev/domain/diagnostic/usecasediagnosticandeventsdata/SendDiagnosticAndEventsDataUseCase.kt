package com.papmobdev.domain.diagnostic.usecasediagnosticandeventsdata

import com.papmobdev.domain.FlowUseCase
import com.papmobdev.domain.diagnostic.models.SendDiagnosticAndEventsModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface SendDiagnosticAndEventsDataUseCase : FlowUseCase<SendDiagnosticAndEventsModel, Boolean>