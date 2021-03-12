package com.papmobdev.domain.cars.models

enum class TypesSource(val idType: Int) {
    FRONT_PANEL(1),
    ENGINE(2),
    WHEEL(3);

    companion object {
        fun getTypesSourceById(id: Int) = values()[id - 1]
    }
}

