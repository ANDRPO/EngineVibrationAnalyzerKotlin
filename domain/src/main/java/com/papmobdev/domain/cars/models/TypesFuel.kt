package com.papmobdev.domain.cars.models

enum class TypesFuel(val idType: Int) {
    PETROL(1),
    DIESEL(2);

    companion object {
        fun getTypesSourceById(id: Int) = values()[id - 1]
    }
}