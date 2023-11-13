package com.example.quickmart.models

import java.io.Serializable

data class FavouriteItem(
    val productId: String,
    val productName: String,
    val productImage : String,
    val unitPrice: Double
) : Serializable

