package com.papmobdev.domain.cars.models

data class CarGeneration(
    override var id: Int,
    override var name: String
) : BaseCarOption()
