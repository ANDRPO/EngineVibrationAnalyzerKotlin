package com.papmobdev.domain.cars.models

data class CarModel(
    override var id: Int,
    override var name: String
) : BaseCarOption()
