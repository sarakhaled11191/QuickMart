package com.example.quickmart.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val productName: String,
    @ColumnInfo
    val productImage: String,
    @ColumnInfo
    val unitPrice: Double
)