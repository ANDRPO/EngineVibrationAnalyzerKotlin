package com.papmobdev.enginevibrationanalyzerkotlin.app.koin

import com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.diagnostic.DiagnosticViewModel
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.manual.ManualViewModel
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.selectconfigurationcar.CarParameterListViewModel
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.selectconfigurationcar.SelectCarViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@ExperimentalCoroutinesApi
internal val selectCarModule = module {
    viewModel { SelectCarViewModel(get(), get(), get(), get(), get(), get()) }
}

@ExperimentalCoroutinesApi
internal val carParameterModule = module {
    viewModel { CarParameterListViewModel(get(), get(), get(), get(), get()) }
}

@ExperimentalCoroutinesApi
internal val manualActivityModule = module {
    viewModel { ManualViewModel(get()) }
}

@ExperimentalCoroutinesApi
internal val diagnosticActivityModule = module {
    viewModel { DiagnosticViewModel(get()) }
}

@ExperimentalCoroutinesApi
internal val presentationModule = module {
    loadKoinModules(
        listOf(
            selectCarModule,
            carParameterModule,
            manualActivityModule,
            diagnosticActivityModule
        )
    )
}