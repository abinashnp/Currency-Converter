package com.abinash.currencyconverter.persistent

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val prefModule = module {
    factory { PrefManager(androidApplication()) }
}

class PrefManager(context: Context) {


    private val LENGTH = "_length"
    private val DEFAULT_STRING_VALUE = ""
    private val DEFAULT_INT_VALUE = -1
    private val DEFAULT_DOUBLE_VALUE = -1.0
    private val DEFAULT_FLOAT_VALUE = -1f
    private val DEFAULT_LONG_VALUE = -1L
    private val DEFAULT_BOOLEAN_VALUE = false
    private var sharedPreferences = context.applicationContext.getSharedPreferences(
        context.packageName + "_preferences",
        Context.MODE_PRIVATE
    )


    // String related methods
    /**
     * @param what
     * @return Returns the stored value of 'what'
     */
    fun read(what: String?): String? {
        return sharedPreferences.getString(what, DEFAULT_STRING_VALUE)
    }

    /**
     * @param what
     * @param defaultString
     * @return Returns the stored value of 'what'
     */
    fun read(what: String?, defaultString: String?): String? {
        return sharedPreferences.getString(what, defaultString)
    }

    /**
     * @param where
     * @param what
     */
    fun write(where: String?, what: String?) {
        sharedPreferences.edit().putString(where, what).apply()
    }
    // int related methods
    /**
     * @param what
     * @return Returns the stored value of 'what'
     */
    fun readInt(what: String?): Int {
        return sharedPreferences.getInt(what, DEFAULT_INT_VALUE)
    }

    /**
     * @param what
     * @param defaultInt
     * @return Returns the stored value of 'what'
     */
    fun readInt(what: String?, defaultInt: Int): Int {
        return sharedPreferences.getInt(what, defaultInt)
    }

    /**
     * @param where
     * @param what
     */
    fun writeInt(where: String?, what: Int) {
        sharedPreferences.edit().putInt(where, what).apply()
    }
    // double related methods
    /**
     * @param what
     * @return Returns the stored value of 'what'
     */
    fun readDouble(what: String?): Double {
        return if (!contains(what)) DEFAULT_DOUBLE_VALUE else java.lang.Double.longBitsToDouble(
            readLong(what)
        )
    }

    /**
     * @param what
     * @param defaultDouble
     * @return Returns the stored value of 'what'
     */
    fun readDouble(what: String?, defaultDouble: Double): Double {
        return if (!contains(what)) defaultDouble else java.lang.Double.longBitsToDouble(
            readLong(
                what
            )
        )
    }

    /**
     * @param where
     * @param what
     */
    fun writeDouble(where: String?, what: Double) {
        writeLong(where, java.lang.Double.doubleToRawLongBits(what))
    }
    // float related methods
    /**
     * @param what
     * @return Returns the stored value of 'what'
     */
    fun readFloat(what: String?): Float {
        return sharedPreferences.getFloat(what, DEFAULT_FLOAT_VALUE)
    }

    /**
     * @param what
     * @param defaultFloat
     * @return Returns the stored value of 'what'
     */
    fun readFloat(what: String?, defaultFloat: Float): Float {
        return sharedPreferences.getFloat(what, defaultFloat)
    }

    /**
     * @param where
     * @param what
     */
    fun writeFloat(where: String?, what: Float) {
        sharedPreferences.edit().putFloat(where, what).apply()
    }
    // long related methods
    /**
     * @param what
     * @return Returns the stored value of 'what'
     */
    fun readLong(what: String?): Long {
        return sharedPreferences.getLong(what, DEFAULT_LONG_VALUE)
    }

    /**
     * @param what
     * @param defaultLong
     * @return Returns the stored value of 'what'
     */
    fun readLong(what: String?, defaultLong: Long): Long {
        return sharedPreferences.getLong(what, defaultLong)
    }

    /**
     * @param where
     * @param what
     */
    fun writeLong(where: String?, what: Long) {
        sharedPreferences.edit().putLong(where, what).apply()
    }
    // boolean related methods
    /**
     * @param what
     * @return Returns the stored value of 'what'
     */
    fun readBoolean(what: String?): Boolean {
        return sharedPreferences.getBoolean(what, DEFAULT_BOOLEAN_VALUE)
    }

    /**
     * @param what
     * @param defaultBoolean
     * @return Returns the stored value of 'what'
     */
    fun readBoolean(what: String?, defaultBoolean: Boolean): Boolean {
        return sharedPreferences.getBoolean(what, defaultBoolean)
    }

    /**
     * @param where
     * @param what
     */
    fun writeBoolean(where: String?, what: Boolean) {
        sharedPreferences.edit().putBoolean(where, what).apply()
    }
    // String set methods
    /**
     * @param key
     * @param value
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    fun putStringSet(key: String, value: Set<String?>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            sharedPreferences.edit().putStringSet(key, value).apply()
        } else {
            // Workaround for pre-HC's lack of StringSets
            putOrderedStringSet(key, value)
        }
    }

    /**
     * @param key
     * @param value
     */
    fun putOrderedStringSet(key: String, value: Set<String?>) {
        var stringSetLength = 0
        if (sharedPreferences.contains(key + LENGTH)) {
            // First read what the value was
            stringSetLength = readInt(key + LENGTH)
        }
        writeInt(key + LENGTH, value.size)
        var i = 0
        for (aValue in value) {
            write("$key[$i]", aValue)
            i++
        }
        while (i < stringSetLength) {

            // Remove any remaining values
            remove("$key[$i]")
            i++
        }
    }

    /**
     * @param key
     * @param defValue
     * @return Returns the String Set with HoneyComb compatibility
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    fun getStringSet(key: String, defValue: Set<String?>?): Set<String?>? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            sharedPreferences.getStringSet(key, defValue)
        } else {
            // Workaround for pre-HC's missing getStringSet
            getOrderedStringSet(key, defValue)
        }
    }

    /**
     * @param key
     * @param defValue
     * @return Returns the ordered String Set
     */
    fun getOrderedStringSet(key: String, defValue: Set<String?>?): Set<String?>? {
        if (contains(key + LENGTH)) {
            val set = LinkedHashSet<String?>()
            val stringSetLength = readInt(key + LENGTH)
            if (stringSetLength >= 0) {
                for (i in 0 until stringSetLength) {
                    set.add(read("$key[$i]"))
                }
            }
            return set
        }
        return defValue
    }
    // end related methods
    /**
     * @param key
     */
    fun remove(key: String) {
        if (contains(key + LENGTH)) {
            // Workaround for pre-HC's lack of StringSets
            val stringSetLength = readInt(key + LENGTH)
            if (stringSetLength >= 0) {
                sharedPreferences.edit().remove(key + LENGTH).apply()
                for (i in 0 until stringSetLength) {
                    sharedPreferences.edit().remove("$key[$i]").apply()
                }
            }
        }
        sharedPreferences.edit().remove(key).apply()
    }

    /**
     * @param key
     * @return Returns if that key exists
     */
    operator fun contains(key: String?): Boolean {
        return sharedPreferences.contains(key)
    }

    /**
     * Clear all the preferences
     */
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
    }
}