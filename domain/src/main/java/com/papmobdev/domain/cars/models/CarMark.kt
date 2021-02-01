package com.papmobdev.domain.cars.models

data class CarMark(
    override var id: Int,
    override var name: String
) : BaseCarOption()
