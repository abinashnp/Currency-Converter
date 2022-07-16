package com.abinash.currencyconverter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abinash.currencyconverter.model.Conversion
import com.abinash.currencyconverter.model.ConversionRate
import com.abinash.currencyconverter.model.Currency
import com.abinash.currencyconverter.repo.RoomDBRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeVM(var repo: RoomDBRepo) : ViewModel() {

    private var list: ArrayList<ConversionRate> = arrayListOf()


    val data = MutableLiveData<ArrayList<ConversionRate>>()

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    val currencies = MutableLiveData<List<String>>()

    private lateinit var conversions: HashMap<String, Double>

    private var hasData = false

    fun start() {
        CoroutineScope(coroutineContext).launch {
            conversions = listToMap(repo.getAllConversions())
            hasData = true
            currencies.postValue(currencyToSymbol(repo.getAllCurrencies()))
        }
    }

    fun update(fromCurrency:String, fromAmount: Double) {
        viewModelScope.launch {
            if (hasData) {
                if (list.isNotEmpty()) {
                    list = arrayListOf()
                }
                currencies.value?.forEach {
                    if (conversions.containsKey(fromCurrency) && conversions.containsKey(it)) {
                        val toRate = conversions[it]
                        val fromRate = conversions[fromCurrency]
                        val exchangeRate = toRate?.div(fromRate!!)
                        val amount =
                            fromAmount * exchangeRate!!
                        val rate = ConversionRate(amount, it)
                        list.add(rate)
                    }
                }
                data.postValue(list)
            }
        }
    }

    fun getCurrencyPos(currency:String): Int {
        return currencies.value?.indexOf(currency)!!
    }

    private fun listToMap(list: List<Conversion>): HashMap<String, Double> {
        val map = HashMap<String, Double>()
        list.forEach {
            map[it.currency] = it.value
        }
        return map
    }

    private fun currencyToSymbol(currencies: List<Currency>): List<String> {
        val list = arrayListOf<String>()
        currencies.forEach {
            list.add(it.symbol)
        }
        return list
    }
}