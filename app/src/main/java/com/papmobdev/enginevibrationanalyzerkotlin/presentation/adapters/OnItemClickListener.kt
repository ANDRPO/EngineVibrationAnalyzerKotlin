package com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters

interface OnItemClickListener {
    fun <T : BaseCarOption> onClick(baseCarOption: T)
}