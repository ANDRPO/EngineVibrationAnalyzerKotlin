package com.papmobdev.enginevibrationanalyzerkotlin.presentation.feature

import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["setSelectedItemPosition"])
fun AppCompatSpinner.setSelectedItemPosition(position: Int) {
    if (position - 1 <= this.count) {
        this.setSelection(position - 1)
    }
}