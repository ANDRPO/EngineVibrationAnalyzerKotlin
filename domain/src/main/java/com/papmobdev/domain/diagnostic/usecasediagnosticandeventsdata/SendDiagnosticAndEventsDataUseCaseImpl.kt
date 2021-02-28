package com.papmobdev.domain.diagnostic.usecasediagnosticandeventsdata

import com.papmobdev.domain.diagnostic.DiagnosticDataSource
import com.papmobdev.domain.diagnostic.models.SendDiagnosticAndEventsModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class SendDiagnosticAndEventsDataUseCaseImpl(
    private val diagnosticDataSource: DiagnosticDataSource
) : SendDiagnosticAndEventsDataUseCase {

    override fun execute(param: SendDiagnosticAndEventsModel): Flow<Result<Boolean>> = try {
        flow {
            diagnosticDataSource.sendDiagnosticAndEventsData(
                diagnostic = param.diagnosticModel,
                list = param.listEvents
            )
            Result.success(true)
        }
    } catch (e: Exception) {
        flow {
            Result.success(false)
        }
    }
}