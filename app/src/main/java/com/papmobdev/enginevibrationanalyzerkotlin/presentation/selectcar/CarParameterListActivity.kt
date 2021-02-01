package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import android.os.Bundle
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivityCarParameterListBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.CarParametersAdapters
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class CarParameterListActivity : BaseActivity() {

    private val viewModel: CarParameterListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding<ActivityCarParameterListBinding>(R.layout.activity_car_parameter_list).value.apply {
            lifecycleOwner = this@CarParameterListActivity
            viewModel = this@CarParameterListActivity.viewModel.apply {
                getMark().observe(this@CarParameterListActivity) {
                    val linearLayoutManager = LinearLayoutManager(root.context)
                    val carParametersAdapters = CarParametersAdapters(it.getOrNull()!!)
                    recyclerViewCarParameter.addItemDecoration(DividerItemDecoration(recyclerViewCarParameter.context, DividerItemDecoration.VERTICAL))
                    recyclerViewCarParameter.layoutManager = linearLayoutManager
                    recyclerViewCarParameter.adapter = carParametersAdapters
                }
            }
        }
    }
}