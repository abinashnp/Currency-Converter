package com.abinash.currencyconverter.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abinash.currencyconverter.model.Conversion
import com.abinash.currencyconverter.model.ConversionRate
import com.abinash.currencyconverter.model.Currency

@Dao
interface ConversionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(conversion: Conversion)


    @Query("SELECT  (SELECT value FROM conversion_table  WHERE currency = :from) / (SELECT value FROM conversion_table  WHERE currency = :to)")
    fun getValue(from:String, to:String):Double


    @Query("SELECT COUNT(*) FROM conversion_table")
    fun count():Int


    @Query("SELECT * FROM conversion_table")
    fun listAllConversions():List<Conversion>


}