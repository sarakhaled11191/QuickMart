package com.example.quickmart.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class FavouriteItemDetails(
    @Embedded
    val favouriteEntity: FavouriteEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val productEntity: ProductEntity
)