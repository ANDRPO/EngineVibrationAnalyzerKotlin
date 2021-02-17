package com.papmobdev.domain.cars

import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCase
import com.papmobdev.domain.cars.usecasecarmarks.GetMarksUseCase
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.ObserveConfigurationCarUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.UpdateConfigurationCarUseCase
import com.papmobdev.domain.cars.usecasetypesfuels.GetTypesFuelUseCase

class InteractorCarsImpl(
    private val getMarksUseCase: GetMarksUseCase,
    private val getModelsUseCase: GetModelsUseCase,
    private val getGenerationsUseCase: GetGenerationsUseCase,
    private val observeConfigurationCarUseCase: ObserveConfigurationCarUseCase,
    private val updateConfigurationCarUseCase: UpdateConfigurationCarUseCase,
    private val getTypesFuelUseCase: GetTypesFuelUseCase
) {

}