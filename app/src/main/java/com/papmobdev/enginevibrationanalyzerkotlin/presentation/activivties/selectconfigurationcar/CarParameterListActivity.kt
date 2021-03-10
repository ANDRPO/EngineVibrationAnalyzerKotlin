package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.selectconfigurationcar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.papmobdev.domain.cars.CodeParametersCar
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivityCarParameterListBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.CarParametersAdapters
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.OnItemClickListener
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.ParametersModel
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_car_parameter_list.*
import kotlinx.android.synthetic.main.activity_car_parameter_list.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class CarParameterListActivity : BaseActivity(), OnItemClickListener {

    private lateinit var binding: ActivityCarParameterListBinding

    private val viewModel: CarParameterListViewModel by viewModel()
    private val adapter: CarParametersAdapters by lazy {
        binding.searchCarEditText.searchCarEditText.setText("")
        CarParametersAdapters(this)
    }

    companion object {
        private const val KEY_TYPE_CAR_OPTION = "key_type_car_option"

        fun start(context: Context, codeParametersCar: CodeParametersCar) {
            val intent = Intent(context, CarParameterListActivity::class.java)
            intent.putExtra(KEY_TYPE_CAR_OPTION, codeParametersCar)
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
        viewModel.codeParametersCar = getCarOption()
        viewModel.fetchData()
    }

    private fun initObservable() {
        viewModel.apply {
            listParameters.observe(this@CarParameterListActivity, {
                it?.let { list -> createRecyclerView(list) }
            })
            listParametersCopy.observe(this@CarParameterListActivity, { list ->
                adapter.submitList(list)
                binding.apply {
                    list?.size.also {
                        notifyEmptyListParameters.visibility =
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
    private fun createRecyclerView(it: MutableList<ParametersModel>) {
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

    override fun onClick(item: ParametersModel) {
        viewModel.updateConfiguration(getCarOption(), item)
        finish()
    }

    private fun getCarOption(): CodeParametersCar {
        val carOption = this.intent?.getSerializableExtra(KEY_TYPE_CAR_OPTION)
            ?.let { return@let it } ?: this@CarParameterListActivity.finish()
        return carOption as CodeParametersCar
    }


    private fun showToast(message: String) {
        Toast.makeText(this@CarParameterListActivity, message, Toast.LENGTH_SHORT).show()
    }

}