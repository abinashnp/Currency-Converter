package com.abinash.currencyconverter.util

import com.abinash.currencyconverter.model.Conversion
import com.abinash.currencyconverter.model.Currency
import org.json.JSONObject

class JSONHelper {

    companion object {
        fun getCurrencies(data: String): ArrayList<Currency> {
            val currencies = arrayListOf<Currency>()
            try {
                val jsonResponse = JSONObject(data)
                jsonResponse.keys().forEach {
                    val symbol = it
                    val name = jsonResponse.getString(symbol)
                    currencies.add(Currency(symbol = symbol, name = name))
                }
            } catch (e: Exception) {
                return currencies;
            }
            return currencies
        }


        fun getConversions(data: String): ArrayList<Conversion> {
            val conversions = arrayListOf<Conversion>()
            try {
                val response = JSONObject(data)
                if (response.has("rates")) {
                    val rates = response.getJSONObject("rates")
                    rates.keys().forEach {
                        val currency = it
                        val cValue = rates.getDouble(currency)
                        conversions.add(Conversion(currency, cValue))
                    }
                }
            } catch (e: Exception) {
                return conversions;
            }
            return conversions
        }


        fun getTimeStamp(data: String): Double {
            return try {
                val response = JSONObject(data)
                return response.getInt("timestamp").toDouble()
            } catch (e: Exception) {
                0.0
            }
        }

    }
}