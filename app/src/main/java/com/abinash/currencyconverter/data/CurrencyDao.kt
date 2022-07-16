package com.abinash.currencyconverter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abinash.currencyconverter.model.Currency

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(currency: Currency)

    @Query("SELECT * FROM currency_table")
    fun listAllCurrencies():List<Currency>


    @Query("SELECT COUNT(*) FROM currency_table")
    fun count():Int

}