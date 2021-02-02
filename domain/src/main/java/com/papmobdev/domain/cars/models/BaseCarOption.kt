package com.papmobdev.domain.cars.models

import android.os.Parcelable

abstract class BaseCarOption : Parcelable {
    abstract var id: Int
    abstract var name: String
}