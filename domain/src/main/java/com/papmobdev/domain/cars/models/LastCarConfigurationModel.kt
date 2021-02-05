package com.papmobdev.domain.cars.models

data class LastCarConfigurationModel(
    val fkCarMark: Int?,
    val nameMark: String?,
    val fkCarModel: Int?,
    val nameModel: String?,
    val fkCarGeneration: Int?,
    val nameGeneration: String?,
)
