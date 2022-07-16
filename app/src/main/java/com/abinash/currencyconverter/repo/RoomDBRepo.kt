package com.abinash.currencyconverter.repo

import android.content.Context
import android.util.Log
import com.abinash.currencyconverter.R
import com.abinash.currencyconverter.data.CurrencyRoomDB
import com.abinash.currencyconverter.model.Conversion
import com.abinash.currencyconverter.model.Currency
import com.abinash.currencyconverter.persistent.PrefManager
import com.abinash.currencyconverter.util.JSONHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val roomDBRepoModule = module {
    factory { RoomDBRepo(androidApplication(), get()) }
}

class RoomDBRepo(private val context: Context, private val prefManager: PrefManager) {


    private var db= CurrencyRoomDB.getInstance(context)


    private fun insertCurrency(currency: Currency){
        db?.dbWriteExecutor?.execute {
            db?.currencyDao()?.insertOrUpdate(currency)
        }
    }


    fun getAllCurrencies(): List<Currency> {
        return db?.currencyDao()?.listAllCurrencies()!!
    }



    fun getAllConversions(): List<Conversion> {
        return db?.conversionDao()?.listAllConversions()!!
    }
    


    fun countCurrencies(): Int? {
        return db?.currencyDao()?.count()
    }


    fun countConversion(): Int? {
        return db?.conversionDao()?.count()
    }

    fun convert(from: String, to: String): Double? {
        return db?.conversionDao()?.getValue(from, to)
    }


    private fun insertConversion(conversion: Conversion){
        db?.dbWriteExecutor?.execute {
            db?.conversionDao()?.insertOrUpdate(conversion)
        }
    }

    fun handleCurrencyResponse(data:String){
        JSONHelper.getCurrencies(data).forEach {
            insertCurrency(it)
        }
    }


    fun handleConversionResponse(data:String){
        JSONHelper.getConversions(data).forEach {
            insertConversion(it)
        }

        val currentTime=System.currentTimeMillis()
        prefManager.writeLong(context.getString(R.string.lastread), currentTime)
        prefManager.writeDouble(context.getString(R.string.lasttimestamp), JSONHelper.getTimeStamp(data))
    }
}
