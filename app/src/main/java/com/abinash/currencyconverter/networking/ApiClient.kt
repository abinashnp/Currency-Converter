package com.abinash.currencyconverter.networking

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val apiClientModule= module {
    single { ApiClient()}
}

class ApiClient {

    private val baseUrl = "https://openexchangerates.org/"
    fun getApi(): Api {
        val client =
            OkHttpClient.Builder().addInterceptor { chain: Interceptor.Chain ->
                val request = chain.request()
                chain.proceed(request)
            }.build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(Api::class.java)
    }
}

