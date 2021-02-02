package com.papmobdev.domain.cars.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CarMark(
    override var id: Int,
    override var name: String
) : BaseCarOption()