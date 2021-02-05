package com.papmobdev.enginevibrationanalyzerkotlin.presentation.feature

import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter(value = ["setAdapter"])
fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>){
    this.adapter = adapter
}

@BindingAdapter(value = ["onTextChanged"])
fun EditText.onTextChanged(onTextChanged: TextWatcher){
    this.addTextChangedListener(onTextChanged)
}