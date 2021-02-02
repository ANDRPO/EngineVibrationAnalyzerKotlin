package com.papmobdev.domain.cars.models

import kotlinx.android.parcel.Parcelize

@Parcelize
data class CarModel(
    override var id: Int,
    override var name: String
) : BaseCarOption()
