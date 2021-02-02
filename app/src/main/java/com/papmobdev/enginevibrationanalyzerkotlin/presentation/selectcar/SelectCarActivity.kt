package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import com.papmobdev.domain.cars.CodeContractSelectCar
import com.papmobdev.domain.cars.CodeOptionsCar
import com.papmobdev.domain.cars.models.BaseCarOption
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivitySelectCarBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class SelectCarActivity : BaseActivity() {

    private val viewModel: SelectCarViewModel by viewModel()
    private lateinit var binding: ActivitySelectCarBinding

    private val regex = Regex("[0-9]|[0-9].[0-9]")

    private val listFuel = listOf<String>("Дизель", "Бензин")

    private val resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val data: Intent = result.data!!
                viewModel.postDataOption(
                    data.getParcelableExtra(CodeContractSelectCar.OBJ_OPTION),
                    data.getBooleanExtra(CodeContractSelectCar.NEXT_OPTION_IS_NULL, false)
                )
            }
        }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = binding<ActivitySelectCarBinding>(R.layout.activity_select_car).value.apply {
            lifecycleOwner = this@SelectCarActivity
            viewModel = this@SelectCarActivity.viewModel
            val adapter = ArrayAdapter<String>(
                this@SelectCarActivity,
                R.layout.support_simple_spinner_dropdown_item,
                listFuel
            )
            //     binding.spinnerDieselOrPetrol.dropDownVerticalOffset(R.layout.support_simple_spinner_dropdown_item)
            spinnerDieselOrPetrol.adapter = adapter
        }

        initObervers()
        initClickListeners()
    }

    private fun initObervers() {
        viewModel.liveDataMark.observe(this@SelectCarActivity, Observer {
            binding.objCarMark = it
        })
        viewModel.liveDataModel.observe(this@SelectCarActivity, Observer {
            binding.objCarModel = it
        })
        viewModel.liveDataGeneration.observe(this@SelectCarActivity, Observer {
            binding.objCarGeneration = it
        })
    }

    private fun initClickListeners() {
        binding.selectMarkCar.setOnClickListener {
            openCarParameterList(CodeOptionsCar.MARK, null)
        }
        binding.selectModelCar.setOnClickListener {
            if (modelCheckSelection()) {
                openCarParameterList(CodeOptionsCar.MODEL, viewModel.liveDataMark.value?.id)
            }
        }
        binding.selectGenerationCar.setOnClickListener {
            if (generationCheckSelection()) {
                openCarParameterList(CodeOptionsCar.GENERATION, viewModel.liveDataModel.value?.id)
            }
        }
    }

    private fun modelCheckSelection(): Boolean {
        return binding.objCarMark != null && viewModel.nextModelIsNotNull
    }

    private fun generationCheckSelection(): Boolean {
        return binding.objCarModel != null && viewModel.nextGenerationIsNotNull
    }

    private fun openCarParameterList(typeOptionCars: Int, id: Int?) {
        val intent = Intent(this, CarParameterListActivity::class.java)
        intent.putExtra(CodeContractSelectCar.CODE_OPTIONS_CAR, typeOptionCars)
        intent.putExtra(CodeContractSelectCar.ID, id)
        resultLauncher.launch(intent)
    }

    private fun showToastNotLastSelect(message: String) {
        Toast.makeText(this@SelectCarActivity, message, Toast.LENGTH_LONG).show()
    }
}