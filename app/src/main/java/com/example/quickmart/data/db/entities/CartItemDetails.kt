package com.example.quickmart.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CartItemDetails(
    @Embedded
    val cartEntity: CartEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val productEntity: ProductEntity
)