package com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters

import com.papmobdev.domain.cars.models.BaseCarOption

interface OnItemClickListener {
    fun <T : BaseCarOption> onClick(baseCarOption: T)
}