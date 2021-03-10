package com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters

import androidx.recyclerview.widget.DiffUtil

class SearchFilterDiffUtils : DiffUtil.ItemCallback<ParametersModel>() {
    override fun areItemsTheSame(oldItem: ParametersModel, newItem: ParametersModel): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ParametersModel, newItem: ParametersModel): Boolean =
        oldItem == newItem

}