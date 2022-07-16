package com.abinash.currencyconverter.util

import com.abinash.currencyconverter.TestHelper
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class JSONHelperTest {


    @Test
    fun `test total data size with correct data`() {
        val data = TestHelper.readFileWithoutNewLineFromResources("correctConversionResponse.json")

        val list = JSONHelper.getConversions(data)

        assertThat(list.size).isNotEqualTo(0)
    }


    @Test
    fun `test total data size with correct data 2`() {
        val data = TestHelper.readFileWithoutNewLineFromResources("correctConversionResponse.json")

        val list = JSONHelper.getConversions(data)

        assertThat(list.size).isEqualTo(169)
    }


    @Test
    fun `test total data size with wrong data`() {
        val data = TestHelper.readFileWithoutNewLineFromResources("wrongConversionResponse.json")

        val list = JSONHelper.getConversions(data)

        assertThat(list.size).isEqualTo(0)
    }


    @Test
    fun `test total data size with wrong data 2`() {
        val data = TestHelper.readFileWithoutNewLineFromResources("wrongConversionResponse.json")

        val list = JSONHelper.getConversions(data)

        assertThat(list.size).isNotEqualTo(169)
    }

    @Test
    fun `timestamp simple false test with correct data`() {
        val data = TestHelper.readFileWithoutNewLineFromResources("correctConversionResponse.json")

        val timestamp = JSONHelper.getTimeStamp(data)

        assertThat(timestamp).isNotEqualTo(0)
    }


    @Test
    fun `timestamp simple true test with correct data`() {
        val data = TestHelper.readFileWithoutNewLineFromResources("correctConversionResponse.json")

        val timestamp = JSONHelper.getTimeStamp(data)

        assertThat(timestamp).isEqualTo(1656687600.0)
    }



    @Test
    fun `timestamp simple false test with wrong data`() {
        val data = TestHelper.readFileWithoutNewLineFromResources("wrongConversionResponse.json")

        val timestamp = JSONHelper.getTimeStamp(data)

        assertThat(timestamp).isEqualTo(0.0)
    }


    @Test
    fun `timestamp simple true test with wrong data`() {
        val data = TestHelper.readFileWithoutNewLineFromResources("wrongConversionResponse.json")

        val timestamp = JSONHelper.getTimeStamp(data)

        assertThat(timestamp).isNotEqualTo(1656687600.0)
    }




}