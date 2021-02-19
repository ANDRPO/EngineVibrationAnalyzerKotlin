package com.papmobdev.domain.diagnostic.usecasesensorevents

import com.papmobdev.domain.diagnostic.DiagnosticDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class SendListSensorEventsUseCaseImpl(
    private val diagnosticDataSource: DiagnosticDataSource
) : SendListSensorEventsUseCase {
    override fun execute(param: SendEventModel): Flow<Result<Boolean>> = try {
        flow {
            diagnosticDataSource.sendEvents(param.idDiagnostic, param.listEvents)
            Result.success(true)
        }
    } catch (e: Exception) {
        flow {
            Result.success(false)
        }
    }

}