package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.papmobdev.domain.cars.CodeOptionsCar
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivityCarParameterListBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.OnItemClickListener
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class CarParameterListActivity : BaseActivity(), OnItemClickListener {

    private lateinit var binding: ActivityCarParameterListBinding
    private val viewModel: CarParameterListViewModel by viewModel()

    companion object {
        private const val KEY_TYPE_CAR_OPTION = "key_type_car_option"

        fun start(context: Context, valueTypeOption: String) {
            context.startActivity(
                Intent(context, CarParameterListActivity::class.java).apply {
                    putExtra(KEY_TYPE_CAR_OPTION, valueTypeOption)
                }
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            binding<ActivityCarParameterListBinding>(R.layout.activity_car_parameter_list).value.apply {
                lifecycleOwner = this@CarParameterListActivity
                viewModel = this@CarParameterListActivity.viewModel
            }
    }

    override fun <T> onClick(baseCarOption: T) {
        val intent = Intent()

        intent.putExtra(,)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun getCarType(): CodeOptionsCar =
        this.intent.getParcelableExtra()

    private fun getIdOption(): Int = this.intent.getIntExtra()

}