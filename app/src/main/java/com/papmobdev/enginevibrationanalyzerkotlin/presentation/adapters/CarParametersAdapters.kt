package com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ItemParamCarBinding

class CarParametersAdapters(
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<ParametersModel, CarParametersAdapters.ViewHolder>(SearchFilterDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemParamCarBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class ViewHolder(private val binding: ItemParamCarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParametersModel) {
            binding.apply {
                id = item.id
                name = item.name
                rootItem.setOnClickListener { onItemClickListener.onClick(item) }
                binding.executePendingBindings()
            }
        }
    }
}