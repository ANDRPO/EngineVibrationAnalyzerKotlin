package com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters

import androidx.recyclerview.widget.DiffUtil

class SearchFilterDiffUtils(
    private val newOptionsList: List<*>?,
    private val oldOptionsList: List<*>?
) : DiffUtil.Callback() {


    override fun getNewListSize(): Int = newOptionsList?.size ?: 0

    override fun getOldListSize(): Int = oldOptionsList?.size ?: 0

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldOptionsList?.get(oldItemPosition) == newOptionsList?.get(newItemPosition)

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldOptionsList?.get(oldItemPosition) == newOptionsList?.get(newItemPosition)

}