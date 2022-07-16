package com.abinash.currencyconverter.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
class Currency(
    @PrimaryKey
    @NonNull
    var symbol: String,
    @NonNull
    var name: String
) {

}