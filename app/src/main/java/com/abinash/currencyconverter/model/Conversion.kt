package com.abinash.currencyconverter.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversion_table")
class Conversion(
    @PrimaryKey
    @NonNull
    var currency: String,
    @NonNull
    var value: Double
) {

}