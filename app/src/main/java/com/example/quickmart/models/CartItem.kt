package com.example.quickmart.models

import java.io.Serializable

data class CartItem(
    val productId: String,
    val productName: String,
    val productImage : String,
    var quantity: Int,
    val unitPrice: Double
) : Serializable {

    fun calculateTotalPrice(): Double {
        return quantity * unitPrice
    }
}

