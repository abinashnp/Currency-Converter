package com.abinash.currencyconverter.networking

import okhttp3.ResponseBody

interface ResponseHandler {
    fun handleSuccess(response: ResponseBody)
    fun handleError(e: Throwable)
}