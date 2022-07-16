package com.abinash.currencyconverter.networking

import com.abinash.currencyconverter.model.Currency
import com.abinash.currencyconverter.repo.RoomDBRepo
import com.abinash.currencyconverter.viewmodel.SplashVM
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

abstract class NetworkRepoTest {

    private lateinit var roomRepo: RoomDBRepo

    private lateinit var repo: NetworkRepo

    private lateinit var apiClient: ApiClient

    private lateinit var splashVM: SplashVM

    @Before
    @Throws
    fun setup() {

        roomRepo = mock(RoomDBRepo::class.java)

        apiClient = mock(ApiClient::class.java)

        repo = NetworkRepo(apiClient)

        splashVM = SplashVM(roomRepo, repo)

    }


    @Test
    fun fetchCurrencies() {

    }

    @Test
    fun fetchConversions() {
    }
}