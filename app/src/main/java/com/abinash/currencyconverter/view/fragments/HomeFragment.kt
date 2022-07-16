package com.abinash.currencyconverter.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abinash.currencyconverter.R
import com.abinash.currencyconverter.adapter.RatesAdapter
import com.abinash.currencyconverter.databinding.FragmentHomeBinding
import com.abinash.currencyconverter.persistent.PrefManager
import com.abinash.currencyconverter.util.ListItemPadding
import com.abinash.currencyconverter.util.TimeUtil
import com.abinash.currencyconverter.viewmodel.HomeVM
import me.zhanghai.android.fastscroll.FastScrollerBuilder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private var currentAmount: Double = 100.0

    private var currentCurrency: String = "USD"

    private val viewModel: HomeVM by viewModel()

    private val timeAgo: TimeUtil by inject()

    private val prefManager: PrefManager by inject()


    private lateinit var adapter: RatesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.amountField.requestFocus()

        viewModel.start()

        adapter = RatesAdapter(arrayListOf())

        //s
        val manager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        if (binding.priceList.itemDecorationCount == 0) {
            binding.priceList.addItemDecoration(ListItemPadding(10, true))
        }
        binding.priceList.layoutManager = manager

        binding.priceList.adapter = adapter

        FastScrollerBuilder(binding.priceList).setPopupTextProvider { position ->
            adapter.getItem(
                position
            )?.symbol!!
        }.build()


        viewModel.data.observe(viewLifecycleOwner) {
            adapter.update(it)
        }

        binding.tvTimeStamp.text = timeAgo.timeAgo(
            prefManager.readDouble(getString(R.string.lasttimestamp))
                .toLong() * 1000
        )

        binding.amountField.doAfterTextChanged {
            currentAmount = if (it.toString().isNotEmpty()) {
                it.toString().toDouble()
            } else {
                0.0
            }
            viewModel.update(currentCurrency, currentAmount)
        }


        viewModel.currencies.observe(viewLifecycleOwner) {

            currentCurrency = prefManager.read(
                getString(R.string.currentCurrency), "USD"
            )!!

            binding.currencySpinner.setText(currentCurrency)
            val adapter =
                ArrayAdapter(requireContext(), R.layout.list_item, viewModel.currencies.value!!)
            binding.currencySpinner.setAdapter(adapter)
            viewModel.update(currentCurrency, currentAmount)
        }

        binding.currencySpinner.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {
                currentCurrency = binding.currencySpinner.text.toString()
                prefManager.write(
                    getString(R.string.currentCurrency),
                    currentCurrency
                )
                viewModel.update(currentCurrency, currentAmount)
            }
        }

        return binding.root
    }


}