package com.abinash.currencyconverter.networking

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.Response
import okhttp3.ResponseBody
import org.koin.dsl.module


val networkRepoModule = module {
    factory { NetworkRepo(get()) }
}

class NetworkRepo(private var client: ApiClient) {

    private val disposables = CompositeDisposable()

    fun fetchCurrencies(responseHandler: ResponseHandler) {
        disposables.add(
            client.getApi().getCurrencies()
                .subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeWith(object : DisposableSingleObserver<ResponseBody>() {
                    override fun onSuccess(response: ResponseBody) {
                        responseHandler.handleSuccess(response)
                    }

                    override fun onError(e: Throwable) {
                        responseHandler.handleError(e)

                    }
                })
        )
    }

    fun fetchConversions(responseHandler: ResponseHandler) {
        disposables.add(
            client.getApi().getConversionRates("c886f312d37d4ca2a35d3acac0d85ac8")
                .subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeWith(object : DisposableSingleObserver<ResponseBody>() {
                    override fun onSuccess(response: ResponseBody) {
                        responseHandler.handleSuccess(response)
                    }

                    override fun onError(e: Throwable) {
                        responseHandler.handleError(e)
                    }
                })
        )
    }

    fun dispose() {
        disposables.dispose()
    }


}