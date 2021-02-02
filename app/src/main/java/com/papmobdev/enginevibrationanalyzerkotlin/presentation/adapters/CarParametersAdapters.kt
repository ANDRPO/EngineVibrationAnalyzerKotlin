package com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.papmobdev.domain.cars.models.BaseCarOption
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ItemParamCarBinding

class CarParametersAdapters<T : BaseCarOption>(
    private val items: List<T>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CarParametersAdapters<T>.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemParamCarBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(private val binding: ItemParamCarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            binding.carOption = item
            binding.rootItem.setOnClickListener { onItemClickListener.onClick(item) }
            binding.executePendingBindings()
        }
    }
}