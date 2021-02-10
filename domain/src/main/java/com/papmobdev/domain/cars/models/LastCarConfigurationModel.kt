package com.papmobdev.domain.cars.models

data class LastCarConfigurationModel(
    val fkCarMark: Int? = null,
    val nameMark: String? = null,
    val fkCarModel: Int? = null,
    val nameModel: String? = null,
    val fkCarGeneration: Int? = null,
    val nameGeneration: String? = null
)