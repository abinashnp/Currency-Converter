package com.abinash.currencyconverter.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.abinash.currencyconverter.R
import com.abinash.currencyconverter.persistent.PrefManager
import org.koin.android.ext.koin.androidApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module
import java.util.*
import kotlin.math.abs


val timeUtilModule = module {
    factory { TimeUtil(androidApplication()) }
}

class TimeUtil(var context: Context) : KoinComponent {

    private val prefManager: PrefManager by inject()


    @SuppressLint("StringFormatInvalid")
    fun timeAgo(millis: Long): String {
        val diff = Date().time - millis
        val r = context.resources
        val prefix = r.getString(R.string.time_ago_prefix)
        val suffix = r.getString(R.string.time_ago_suffix)
        val seconds = (abs(diff) / 1000).toDouble()
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val years = days / 365
        val words: String = if (seconds < 45) {
            r.getString(R.string.time_ago_seconds, Math.round(seconds))
        } else if (seconds < 90) {
            r.getString(R.string.time_ago_minute, 1)
        } else if (minutes < 45) {
            r.getString(R.string.time_ago_minutes, Math.round(minutes))
        } else if (minutes < 90) {
            r.getString(R.string.time_ago_hour, 1)
        } else if (hours < 24) {
            r.getString(R.string.time_ago_hours, Math.round(hours))
        } else if (hours < 42) {
            r.getString(R.string.time_ago_day, 1)
        } else if (days < 30) {
            r.getString(R.string.time_ago_days, Math.round(days))
        } else if (days < 45) {
            r.getString(R.string.time_ago_month, 1)
        } else if (days < 365) {
            r.getString(R.string.time_ago_months, Math.round(days / 30))
        } else if (years < 1.5) {
            r.getString(R.string.time_ago_year, 1)
        } else {
            r.getString(R.string.time_ago_years, Math.round(years))
        }
        val sb = StringBuilder()
        if (prefix.isNotEmpty()) {
            sb.append(prefix).append(" ")
        }
        sb.append(words)
        if (suffix.isNotEmpty()) {
            sb.append(" ").append(suffix)
        }
        return sb.toString().trim { it <= ' ' }
    }

    fun shouldFetchRates(): Boolean {
        val currentTime = System.currentTimeMillis()
        val prevTimeStamp = prefManager.readLong(context.getString(R.string.lastread))

        val diff = currentTime - prevTimeStamp

        if (diff > 1800000) {
            return true
        }
        return false
    }
}