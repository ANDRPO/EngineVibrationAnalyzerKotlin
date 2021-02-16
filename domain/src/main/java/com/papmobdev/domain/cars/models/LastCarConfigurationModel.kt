package com.papmobdev.domain.cars.models

data class LastCarConfigurationModel(
    val fkCarMark: Int? = null,
    val nameMark: String? = null,
    val fkCarModel: Int? = null,
    val nameModel: String? = null,
    val fkCarGeneration: Int? = null,
    val nameGeneration: String? = null,
    var fkTypeFuel: Int? = 1,
    var engineVolume: Double? = null,
    var note: String? = null
) {
    companion object {
        fun empty() = LastCarConfigurationModel()
    }
}
