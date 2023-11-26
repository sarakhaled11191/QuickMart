package com.example.quickmart.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index("categoryId")]
)
data class ProductEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val description: String,
    @ColumnInfo
    val price: Double,
    @ColumnInfo
    val rating: Int,
    @ColumnInfo
    val imageName: String,
    @ColumnInfo
    val categoryId: Int
)