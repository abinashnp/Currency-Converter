package com.abinash.currencyconverter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abinash.currencyconverter.model.HomeStates
import com.abinash.currencyconverter.model.HomeStates.*
import com.abinash.currencyconverter.networking.ApiClient
import com.abinash.currencyconverter.networking.NetworkRepo
import com.abinash.currencyconverter.networking.ResponseHandler
import com.abinash.currencyconverter.repo.RoomDBRepo
import com.abinash.currencyconverter.util.TimeUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext


class SplashVM(var repo: RoomDBRepo, private val networkRepo: NetworkRepo) : ViewModel(),
    KoinComponent {

    private val timeUtil: TimeUtil by inject()

    val state = MutableLiveData<HomeStates>()
    val message = MutableLiveData<String>()

    var client=ApiClient()

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.IO


    fun start() {
        state.postValue(CHECK_CURRENCY)
        message.postValue("Checking...")
    }


    fun fetchConversions() {
        message.value = "Fetching rates..."
        networkRepo.fetchConversions(object : ResponseHandler {
            override fun handleSuccess(response: ResponseBody) {
                repo.handleConversionResponse(response.string())
                message.postValue("Starting...")
                state.value = FETCH_FINISHED
            }

            override fun handleError(e: Throwable) {
                handleRequestError(e)
            }

        })
    }

    fun checkConversions() {
        CoroutineScope(coroutineContext).launch {
            message.postValue("Updating rates...")
            if (timeUtil.shouldFetchRates()) {
                state.postValue(FETCH_CONVERSION)
            } else if (repo.countConversion()!! < 1) {
                state.postValue(FETCH_CONVERSION)
            } else {
                message.postValue("Starting...")
                state.postValue(FETCH_FINISHED)
            }
        }
    }

    fun fetchCurrencies() {
        viewModelScope.launch {
            client.getApi().getConversionRates("asa").
            map {

            }.catch {  }.collect {  }
        }
        message.value = "Fetching currencies..."
        networkRepo.fetchCurrencies(object : ResponseHandler {
            override fun handleSuccess(response: ResponseBody) {
                repo.handleCurrencyResponse(response.string())
                state.value = CHECK_CONVERSION
            }

            override fun handleError(e: Throwable) {
                handleRequestError(e)
            }

        })
    }

    fun checkCurrencies() {
        message.postValue("Updating currencies...")

        CoroutineScope(coroutineContext).launch {

            val curCount = repo.countCurrencies()

            if (curCount!! < 1) {
                state.postValue(FETCH_CURRENCY)
            } else {
                state.postValue(CHECK_CONVERSION)
            }
        }
    }


    private fun handleRequestError(e: Throwable?) {
        if (e != null) {
            message.value = e.localizedMessage
        } else {
            message.value = "Something went wrong!"
        }
        state.value = ERROR
    }

    override fun onCleared() {
        super.onCleared()
        networkRepo.dispose()
    }

}