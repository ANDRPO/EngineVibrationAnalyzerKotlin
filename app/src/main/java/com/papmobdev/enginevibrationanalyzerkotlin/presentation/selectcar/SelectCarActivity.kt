package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.get
import androidx.lifecycle.Observer
import com.papmobdev.domain.cars.CodeOptionsCar
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivitySelectCarBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class SelectCarActivity : BaseActivity() {

    private val viewModel: SelectCarViewModel by viewModel()
    private lateinit var binding: ActivitySelectCarBinding

    private val regex = Regex("[0-9]|[0-9].[0-9]")

    private val listFuel = listOf("Дизель", "Бензин")

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
        }

        initSpinner()
        initObservers()
        initClickListeners()
    }

    private fun initSpinner() {
        val adapter = ArrayAdapter(
            this@SelectCarActivity,
            R.layout.spinner_item,
            listFuel
        )
        binding.spinnerDieselOrPetrol.adapter = adapter
        binding.spinnerDieselOrPetrol.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.selectOptionFuel = listFuel[position]
                Log.e("SELECTFUEL", listFuel[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun initObservers() {
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
        when (false) {
            binding.objCarMark != null -> showToastNotLastSelect("Не выбрана марка")
            viewModel.nextModelIsNotNull -> showToastNotLastSelect("Список моделей для данной марки отсутствует")
            else -> return true
        }
        return false
    }

    private fun generationCheckSelection(): Boolean {
        when (false) {
            binding.objCarModel != null -> showToastNotLastSelect("Не выбрана модель")
            viewModel.nextGenerationIsNotNull -> showToastNotLastSelect("Список поколений для данной модели отсутствует")
            else -> return true
        }
        return false
    }

    private fun openCarParameterList(typeOptionCars: Int, id: Int?) {
        val intent = Intent(this, CarParameterListActivity::class.java)
        intent.putExtra(CodeContractSelectCar.CODE_OPTIONS_CAR, typeOptionCars)
        intent.putExtra(CodeContractSelectCar.ID, id)
        resultLauncher.launch(intent)
    }

    private fun showToastNotLastSelect(message: String) {
        Toast.makeText(this@SelectCarActivity, message, Toast.LENGTH_SHORT).show()
    }
}