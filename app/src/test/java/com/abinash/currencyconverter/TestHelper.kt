package com.abinash.currencyconverter

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class TestHelper {

    companion object {
        fun readFileWithoutNewLineFromResources(fileName: String): String {
            var inputStream: InputStream? = null
            try {
                inputStream =
                    javaClass.classLoader?.getResourceAsStream(fileName)
                val builder = StringBuilder()
                val reader = BufferedReader(InputStreamReader(inputStream))

                var str: String? = reader.readLine()
                while (str != null) {
                    builder.append(str)
                    str = reader.readLine()
                }
                return builder.toString()
            } finally {
                inputStream?.close()
            }
        }
    }
}