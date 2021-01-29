package com.papmobdev.enginevibrationanalyzerkotlin.app.koin

import com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar.CarParameterListViewModel
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar.SelectCarViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val selectCarModule = module {
    viewModel { SelectCarViewModel() }
}

internal val carParameterModule = module {
    viewModel { CarParameterListViewModel() }
}

internal val presebtationModule = module {
    selectCarModule
    carParameterModule
}