package com.papmobdev.domain.cars.models

data class CarConfiguration(
    val fkCarMark: Int? = null,
    val nameMark: String? = null,
    val fkCarModel: Int? = null,
    val nameModel: String? = null,
    val fkCarGeneration: Int? = null,
    val nameGeneration: String? = null,
    var typeFuel: TypesFuel? = null,
    var typeSource: TypesSource? = null,
    var engineVolume: Double? = null,
    var note: String? = null
)