package com.papmobdev.domain.sensor.models

data class EventModel(
    val id: Int?,
    val x_value: Float,
    val y_value: Float,
    val z_value: Float,
    val timestamp: Long
)
