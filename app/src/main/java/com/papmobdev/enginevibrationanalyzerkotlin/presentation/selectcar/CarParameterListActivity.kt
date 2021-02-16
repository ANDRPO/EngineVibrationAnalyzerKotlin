package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.papmobdev.domain.cars.CodeOptionsCar
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivityCarParameterListBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.CarParametersAdapters
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.OnItemClickListener
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.OptionsModel
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_car_parameter_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class CarParameterListActivity : BaseActivity(), OnItemClickListener {

    private lateinit var binding: ActivityCarParameterListBinding

    private val viewModel: CarParameterListViewModel by viewModel()
    private lateinit var adapter: CarParametersAdapters

    companion object {
        private const val KEY_TYPE_CAR_OPTION = "key_type_car_option"
        fun start(context: Context, codeOptionsCar: CodeOptionsCar) {
            val intent = Intent(context, CarParameterListActivity::class.java)
            intent.putExtra(KEY_TYPE_CAR_OPTION, codeOptionsCar)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            binding<ActivityCarParameterListBinding>(R.layout.activity_car_parameter_list).value.apply {
                lifecycleOwner = this@CarParameterListActivity
                viewModel = this@CarParameterListActivity.viewModel
            }
        initObservable()
        viewModel.fetchData(getCarOption())
        viewModel.carOptionsCar = getCarOption()
    }

    private fun initObservable() {
        viewModel.apply {
            listOptions.observe(this@CarParameterListActivity, {
                createRecyclerView(it)
            })
            listOptionsCopy.observe(this@CarParameterListActivity, { list ->
                adapter.submitList(list)
                binding.apply {
                    list.size.also {
                        notifyEmptyListOptions.visibility =
                            if (it == 0) View.VISIBLE else View.INVISIBLE
                        recyclerViewCarParameter.visibility =
                            if (it == 0) View.INVISIBLE else View.VISIBLE
                    }
                }
            })
            showErrorMessage.observe(this@CarParameterListActivity, {
                showToast(it)
            })

        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun createRecyclerView(it: MutableList<OptionsModel>) {
        adapter = CarParametersAdapters(this@CarParameterListActivity)
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.recyclerViewCarParameter.scrollToPosition(0)
            }
        })
        adapter.submitList(it)
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

    override fun onClick(item: OptionsModel) {
        viewModel.updateConfiguration(getCarOption(), item)
        finish()
    }

    private fun getCarOption(): CodeOptionsCar =
        this.intent?.getSerializableExtra(KEY_TYPE_CAR_OPTION) as CodeOptionsCar

    private fun showToast(message: String) {
        Toast.makeText(this@CarParameterListActivity, message, Toast.LENGTH_SHORT).show()
    }

}