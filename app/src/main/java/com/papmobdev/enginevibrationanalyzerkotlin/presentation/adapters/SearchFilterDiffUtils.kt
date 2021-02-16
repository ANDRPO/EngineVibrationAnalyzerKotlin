package com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters

import androidx.recyclerview.widget.DiffUtil

class SearchFilterDiffUtils : DiffUtil.ItemCallback<OptionsModel>() {
    override fun areItemsTheSame(oldItem: OptionsModel, newItem: OptionsModel): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: OptionsModel, newItem: OptionsModel): Boolean =
        oldItem == newItem

}