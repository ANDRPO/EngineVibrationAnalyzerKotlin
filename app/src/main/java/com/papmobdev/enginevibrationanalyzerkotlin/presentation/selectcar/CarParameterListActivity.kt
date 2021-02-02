package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.papmobdev.domain.cars.CodeContractSelectCar
import com.papmobdev.domain.cars.CodeOptionsCar
import com.papmobdev.domain.cars.models.BaseCarOption
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivityCarParameterListBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.CarParametersAdapters
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.OnItemClickListener
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class CarParameterListActivity : BaseActivity(), OnItemClickListener {

    private lateinit var binding: ActivityCarParameterListBinding
    private val viewModel: CarParameterListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            binding<ActivityCarParameterListBinding>(R.layout.activity_car_parameter_list).value.apply {
                lifecycleOwner = this@CarParameterListActivity
                viewModel = this@CarParameterListActivity.viewModel.apply {
                    when (getCarType()) {
                        CodeOptionsCar.MARK -> this@CarParameterListActivity.getMark()
                        CodeOptionsCar.MODEL -> this@CarParameterListActivity.getModel()
                        CodeOptionsCar.GENERATION -> this@CarParameterListActivity.getGeneration()
                    }
                }
            }
    }

    private fun getMark() {
        this.viewModel.getMarks().observe(this@CarParameterListActivity, Observer {
            createListOptions(it.getOrNull()!!)
        })
    }

    private fun getModel() {
        this.viewModel.getModels(getIdOption()).observe(this, Observer {
            createListOptions(it.getOrNull()!!)
        })
    }

    private fun getGeneration() {
        this.viewModel.getGenerations(getIdOption()).observe(this, Observer {
            createListOptions(it.getOrNull()!!)
        })
    }

    private fun createListOptions(list: List<BaseCarOption>) {
        binding.apply {
            val carParametersAdapters = CarParametersAdapters(list, this@CarParameterListActivity)
            recyclerViewCarParameter.addItemDecoration(
                DividerItemDecoration(
                    recyclerViewCarParameter.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            recyclerViewCarParameter.adapter = carParametersAdapters
        }
    }

    override fun <T : BaseCarOption> onClick(baseCarOption: T) {
        val intent = Intent()
        intent.putExtra(CodeContractSelectCar.OBJ_OPTION, baseCarOption)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun getCarType(): Int =
        this.intent.getIntExtra(CodeContractSelectCar.CODE_OPTIONS_CAR, 1)

    private fun getIdOption(): Int = this.intent.getIntExtra(CodeContractSelectCar.ID, 0)

}