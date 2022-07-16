package com.abinash.currencyconverter.di

import com.abinash.currencyconverter.viewmodel.HomeVM
import com.abinash.currencyconverter.viewmodel.SplashVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    viewModel { SplashVM(get(), get()) }
    viewModel { HomeVM(get()) }
}