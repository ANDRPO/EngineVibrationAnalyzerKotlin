package com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.papmobdev.domain.cars.models.BaseCarOption
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ItemParamCarBinding
import java.util.*

class CarParametersAdapters<T : BaseCarOption>(
    private val items: MutableList<T>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CarParametersAdapters<T>.ViewHolder>() {

    private val itemsCopy = items.toMutableList()

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

    fun filterSearch(paramFilter: String) {
        val paramFilter = paramFilter.toLowerCase(Locale.getDefault())
        items.clear()
        if (paramFilter.isEmpty()) {
            items.addAll(itemsCopy)
        } else {
            for (str in itemsCopy) {
                if (str.name.toLowerCase(Locale.getDefault()).contains(paramFilter)) {
                    items.add(str)
                    Log.e("NameCar", str.name)
                }
            }
        }
        notifyDataSetChanged()
    }
}