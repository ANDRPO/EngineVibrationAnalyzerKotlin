package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.papmobdev.domain.cars.CodeOptionsCar
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivityCarParameterListBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.CarParametersAdapters
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.OnItemClickListener
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_car_parameter_list.*
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
            }
        initObservable()
        if (viewModel.listOptions.value == null) {
            viewModel.fetchData(getCarOption())
            viewModel.carOptionsCar = getCarOption()
        }
    }

    private fun initObservable() {
        viewModel.apply {
            listOptions.observe(this@CarParameterListActivity, {
                createRecyclerView(it)
            })
            diffResult.observe(this@CarParameterListActivity, {
                diffResult.value?.dispatchUpdatesTo(adapter)
            })
            listOptionsCopy.observe(this@CarParameterListActivity, {
                adapter.setData(it)
            })
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun createRecyclerView(it: MutableList<*>) {
        adapter = CarParametersAdapters(it, this@CarParameterListActivity)
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.recyclerViewCarParameter.scrollToPosition(0)
            }
        })

        binding.recyclerViewCarParameter.adapter = adapter
        binding.recyclerViewCarParameter.addItemDecoration(
            DividerItemDecoration(
                recyclerViewCarParameter.context,
                DividerItemDecoration.VERTICAL
            ).apply {
                setDrawable(
                    resources.getDrawable(
                        (R.drawable.drawable_divider_item_decoration),
                        theme
                    )
                )
            }
        )
        adapter.notifyDataSetChanged()
    }

    override fun <T> onClick(item: T) {
        viewModel.updateConfiguration(getCarOption(), item)
        setResult(Activity.RESULT_OK, Intent())
        finish()
    }

    private fun getCarOption(): CodeOptionsCar =
        this.intent?.getSerializableExtra(KEY_TYPE_CAR_OPTION) as CodeOptionsCar


}