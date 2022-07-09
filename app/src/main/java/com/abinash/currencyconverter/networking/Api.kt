package com.abinash.currencyconverter.networking

import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import okhttp3.ResponseBody
import retrofit2.http.*


interface Api {
    @GET("api/currencies.json")
    fun getCurrencies(): Single<ResponseBody>

    @GET("api/latest.json")
    fun getConversionRates(
        @Query("app_id") appId: String
    ): SharedFlow<ResponseBody>

}