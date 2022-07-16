package com.abinash.currencyconverter.app

import android.app.Application
import com.abinash.currencyconverter.di.appModule
import com.abinash.currencyconverter.networking.apiClientModule
import com.abinash.currencyconverter.networking.networkRepoModule
import com.abinash.currencyconverter.persistent.prefModule
import com.abinash.currencyconverter.repo.roomDBRepoModule
import com.abinash.currencyconverter.util.timeUtilModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ConversionApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@ConversionApplication)
            modules(listOf(appModule, apiClientModule, networkRepoModule, roomDBRepoModule, timeUtilModule, prefModule))
        }
    }
}