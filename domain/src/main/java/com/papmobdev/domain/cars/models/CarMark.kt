package com.papmobdev.domain.cars.models

data class CarMark(
    val id: Int?,
    val name: String?
) {
    companion object {
        fun empty() = CarMark(null, null)
    }
}
