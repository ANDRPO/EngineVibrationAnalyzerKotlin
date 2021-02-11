package com.papmobdev.enginevibrationanalyzerkotlin.presentation.feature

import android.text.TextWatcher
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter(value = ["setAdapter"])
fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
    this.adapter = adapter
}

@BindingAdapter(value = ["onTextChanged"])
fun EditText.onTextChanged(onTextChanged: TextWatcher) {
    this.addTextChangedListener(onTextChanged)
}

@BindingAdapter(value = ["setSelectedItemPosition"])
fun AppCompatSpinner.setSelectedItemPosition(position: Int) {
    if (position-1 <= this.count) {
        this.setSelection(position-1)
    }
}