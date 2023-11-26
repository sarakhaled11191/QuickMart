package com.example.quickmart.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ProductWithCategory(
    @Embedded
    val productEntity: ProductEntity,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id",
    )
    val categoryEntity: CategoryEntity
)
