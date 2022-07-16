package com.abinash.currencyconverter.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.abinash.currencyconverter.R
import com.abinash.currencyconverter.databinding.FragmentSplashBinding
import com.abinash.currencyconverter.model.HomeStates.*
import com.abinash.currencyconverter.networking.NetworkUtil
import com.abinash.currencyconverter.viewmodel.SplashVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment() {


    private lateinit var binding: FragmentSplashBinding

    private val viewModel: SplashVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        viewModel.start()

        binding.btnRetry.setOnClickListener {

            if (NetworkUtil().isOnline(context!!)) {
                viewModel.start()
                binding.btnRetry.visibility = View.GONE
                binding.animError.visibility = View.GONE
                binding.animNoInternet.visibility = View.GONE
                binding.animLoading.visibility = View.VISIBLE
            }
        }

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                FETCH_FINISHED -> {
                    lifecycleScope.launch {
                        delay(2000)
                        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                    }
                }
                ERROR -> {
                    binding.btnRetry.visibility = View.VISIBLE
                    if (NetworkUtil().isOnline(context!!)) {
                        binding.animError.visibility = View.VISIBLE
                        binding.animNoInternet.visibility = View.GONE
                        binding.animLoading.visibility = View.GONE
                    } else {
                        binding.animError.visibility = View.GONE
                        binding.animNoInternet.visibility = View.VISIBLE
                        binding.animLoading.visibility = View.GONE
                    }
                }

                CHECK_CURRENCY -> {
                    viewModel.checkCurrencies()
                }
                FETCH_CURRENCY -> {
                    viewModel.fetchCurrencies()
                }
                CHECK_CONVERSION -> {
                    viewModel.checkConversions()
                }
                FETCH_CONVERSION -> {
                    viewModel.fetchConversions()
                }
                else -> {
                    TODO()
                }
            }
        }

        viewModel.message.observe(viewLifecycleOwner) {
            binding.textView.text = it
        }

        return binding.root
    }

}