package com.papmobdev.enginevibrationanalyzerkotlin.presentation.feature

import android.text.TextWatcher
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter(value = ["setSelectedItemPosition"])
fun AppCompatSpinner.setSelectedItemPosition(position: Int) {
    if (position-1 <= this.count) {
        this.setSelection(position-1)
    }
}