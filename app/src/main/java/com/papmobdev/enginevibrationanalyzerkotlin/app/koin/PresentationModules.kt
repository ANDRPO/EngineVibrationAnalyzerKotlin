package com.papmobdev.enginevibrationanalyzerkotlin.app.koin

import com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.diagnostic.ManualViewModel
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.selectconfigurationcar.CarParameterListViewModel
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.selectconfigurationcar.SelectCarViewModel
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.selectconfigurationcar.SelectVibrationSourceViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@ExperimentalCoroutinesApi
internal val selectCarModule = module {
    viewModel { SelectCarViewModel(get(), get(), get(), get(), get()) }
}

@ExperimentalCoroutinesApi
internal val carParameterModule = module {
    viewModel { CarParameterListViewModel(get(), get(), get(), get(), get()) }
}

@ExperimentalCoroutinesApi
internal val manualActivivtyModule = module {
    viewModel { ManualViewModel() }
}

@ExperimentalCoroutinesApi
internal val selectVibrationSourceModule = module {
    viewModel { SelectVibrationSourceViewModel() }
}

@ExperimentalCoroutinesApi
internal val presentationModule = module {
    loadKoinModules(
        listOf(
            selectCarModule,
            carParameterModule,
            selectVibrationSourceModule,
            manualActivivtyModule
        )
    )
}