package com.abinash.currencyconverter

import com.abinash.currencyconverter.di.appModule
import com.abinash.currencyconverter.networking.NetworkRepo
import com.abinash.currencyconverter.networking.ResponseHandler
import okhttp3.Response
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.koin.test.KoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito

@Category(CheckModuleTest::class)
class CheckModulesTest : KoinTest {

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun checkAllModules() = checkModules {
        modules(appModule)
    }

}