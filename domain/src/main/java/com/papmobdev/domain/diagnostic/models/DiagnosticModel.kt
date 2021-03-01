package com.papmobdev.domain.diagnostic.models

import java.util.*

data class DiagnosticModel(
    val idDiagnostic: Long? = null,

    val dateTime: Date,

    val fkCarMark: Int? = null,

    val fkCarModel: Int? = null,

    val fkCarGeneration: Int? = null,

    val fkTypeFuel: Int?,

    val engineVolume: Double? = null,

    val note: String? = null,

    val fkVibrationSource: Int? = null
)
