package com.papmobdev.enginevibrationanalyzerkotlin.presentation.feature

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter(value = ["setAdapter"])
fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>){
    this.adapter = adapter
}

@BindingAdapter(value = ["setOnTextChanged"])
fun EditText.setOnTextChanged(onTextChanged: TextViewBindingAdapter.OnTextChanged){

}