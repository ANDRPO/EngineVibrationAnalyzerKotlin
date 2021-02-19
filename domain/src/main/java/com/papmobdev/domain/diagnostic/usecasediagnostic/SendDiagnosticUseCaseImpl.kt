package com.papmobdev.domain.diagnostic.usecasediagnostic

import com.papmobdev.domain.diagnostic.DiagnosticDataSource
import com.papmobdev.domain.diagnostic.models.DiagnosticModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

@ExperimentalCoroutinesApi
class SendDiagnosticUseCaseImpl(
    private val diagnosticDataSource: DiagnosticDataSource
) : SendDiagnosticUseCase {
    override fun execute(param: DiagnosticModel): Flow<Result<Boolean>> = try {
        flow {
            diagnosticDataSource.sendDiagnostic(param)
            Result.success(true)
        }
    } catch (e: Exception) {
        flow {
            Result.success(false)
        }
    }
}