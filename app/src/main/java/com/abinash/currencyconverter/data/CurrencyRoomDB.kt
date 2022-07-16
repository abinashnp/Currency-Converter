package com.abinash.currencyconverter.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.abinash.currencyconverter.data.CurrencyDao
import kotlin.jvm.Volatile
import com.abinash.currencyconverter.data.CurrencyRoomDB
import androidx.room.Room
import com.abinash.currencyconverter.model.Conversion
import com.abinash.currencyconverter.model.Currency
import java.util.concurrent.Executors

@Database(entities = [Conversion::class, Currency::class], version = 1, exportSchema = false)
abstract class CurrencyRoomDB : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    abstract fun conversionDao(): ConversionDao


    val dbWriteExecutor = Executors.newFixedThreadPool(4)

    companion object {
        @Volatile
        private var INSTANCE: CurrencyRoomDB? = null
        fun getInstance(context: Context): CurrencyRoomDB? {
            if (INSTANCE == null) {
                synchronized(CurrencyRoomDB::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            CurrencyRoomDB::class.java,
                            "currency_db"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}