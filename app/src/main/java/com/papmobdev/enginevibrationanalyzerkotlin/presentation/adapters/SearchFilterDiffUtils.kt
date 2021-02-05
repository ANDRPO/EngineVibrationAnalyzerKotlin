package com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters

import androidx.recyclerview.widget.DiffUtil

class SearchFilterDiffUtils(
    private val newOptionsList: List<*>,
    private val oldOptionsList: List<*>
) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        TODO("Not yet implemented")
    }

    override fun getNewListSize(): Int {
        TODO("Not yet implemented")
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

}