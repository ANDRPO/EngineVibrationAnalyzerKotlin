package com.papmobdev.domain.cars.models

data class CarConfiguration(
    val fkCarMark: Int? = null,
    val nameMark: String? = null,
    val fkCarModel: Int? = null,
    val nameModel: String? = null,
    val fkCarGeneration: Int? = null,
    val nameGeneration: String? = null,
    var fkTypeFuel: Int? = 1,
    var fkTypeSource: Int? = 1,
    var engineVolume: Double? = null,
    var note: String? = null
)