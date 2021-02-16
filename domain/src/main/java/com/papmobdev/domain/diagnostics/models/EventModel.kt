package com.papmobdev.domain.diagnostics.models

data class EventModel(
    val id: Int?,
    val x_value: Double,
    val y_value: Double,
    val z_value: Double,
    val timestamp: Long
)
