package com.papmobdev.enginevibrationanalyzerkotlin.domain.adapters

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.CarGenerationEntity
import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.CarMarkEntity
import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.CarModelEntity
import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.basecarentity.BaseCarEntity

import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ItemParamCarBinding
import java.lang.NullPointerException

class CarParametersAdapters(
    private val items: List<BaseCarEntity>
) : RecyclerView.Adapter<CarParametersAdapters.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarParametersAdapters.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemParamCarBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: ItemParamCarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BaseCarEntity) {
            binding.carOption = item
            binding.executePendingBindings()
        }
    }
}