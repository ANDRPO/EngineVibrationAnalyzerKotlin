package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.papmobdev.domain.cars.CodeOptionsCar
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivityCarParameterListBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.CarParametersAdapters
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.OnItemClickListener
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.SearchFilterDiffUtils
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class CarParameterListActivity : BaseActivity(), OnItemClickListener {

    private lateinit var binding: ActivityCarParameterListBinding

    private val viewModel: CarParameterListViewModel by viewModel()
    private lateinit var adapter: CarParametersAdapters<*>

    companion object {
        private const val KEY_TYPE_CAR_OPTION = "key_type_car_option"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            binding<ActivityCarParameterListBinding>(R.layout.activity_car_parameter_list).value.apply {
                lifecycleOwner = this@CarParameterListActivity
                viewModel = this@CarParameterListActivity.viewModel
                adapter = CarParametersAdapters<Any>(mutableListOf(), this@CarParameterListActivity)
                viewModel.apply {

                }
            }
    }

    fun searchFilter(){

    }

    fun initObservable() {
        viewModel.apply {
            listOptions.observe(this@CarParameterListActivity, {
                adapter = CarParametersAdapters(it, this@CarParameterListActivity)
                binding.recyclerViewCarParameter.adapter = adapter
            })
        }
    }

    fun initOptionList() {
    }

    override fun <T> onClick(item: T) {
        setResult(Activity.RESULT_OK, Intent())
        finish()
    }

    private fun getCarType(): CodeOptionsCar =
        this.intent.getSerializableExtra(KEY_TYPE_CAR_OPTION) as CodeOptionsCar

}