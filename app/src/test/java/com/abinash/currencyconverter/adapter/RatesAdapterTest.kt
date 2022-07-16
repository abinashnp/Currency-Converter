package com.abinash.currencyconverter.adapter

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RatesAdapterTest{


    @Test
    fun `test with large number to see if it contains e`(){
        val formatted=RatesAdapter(listOf()).roundOffDecimal(12345678787886767676767.0)
        assertThat(formatted).doesNotContain("e")
    }

    @Test
    fun `test with smallest number gets cuts off`(){
        val formatted=RatesAdapter(listOf()).roundOffDecimal(0.00000000000000000000000000000000034000)
        assertThat(formatted).isEqualTo("0.00000000000000000000000000000000034")
    }

    @Test
    fun `test with smallest number gets cuts off 2`(){
        val formatted=RatesAdapter(listOf()).roundOffDecimal(0.020000000000000000000000000002)
        assertThat(formatted).isEqualTo("0.02")
    }

    @Test
    fun `test with smallest number gets cuts off 3`(){
        val formatted=RatesAdapter(listOf()).roundOffDecimal(0.020000002)
        assertThat(formatted).isNotEqualTo("0.02")
    }

    @Test
    fun `currency format large trail`(){
        val formatted=RatesAdapter(listOf()).formatAsCurrency("0.020000002")
        assertThat(formatted).isEqualTo("0.020000002")
    }

    @Test
    fun `currency format large`(){
        val formatted=RatesAdapter(listOf()).formatAsCurrency("12310.020000002")
        assertThat(formatted).isEqualTo("12,310.020000002")
    }


    @Test
    fun `currency format large val`(){
        val formatted=RatesAdapter(listOf()).formatAsCurrency("123456789123")
        assertThat(formatted).isEqualTo("123,456,789,123")
    }
}