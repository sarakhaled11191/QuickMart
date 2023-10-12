package com.example.quickmart.data.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.quickmart.data.db.QuickMartDatabase
import com.example.quickmart.data.db.entities.CartEntity
import com.example.quickmart.models.CartItem

class CartRepository(private val db: QuickMartDatabase) {
    var cartItems by mutableStateOf(listOf<CartItem>())

    suspend fun addItem(cartItem: CartItem) {
        val entity = CartEntity(
            id = cartItem.productId,
            productName = cartItem.productName,
            productImage = cartItem.productImage,
            quantity = cartItem.quantity,
            unitPrice = cartItem.unitPrice
        )
        db.getQuickMartDao().addItem(entity)
    }

    suspend fun updateItem(productId: String, quantity: Int) {
        db.getQuickMartDao().updateItem(productId, quantity)
    }

    suspend fun deleteItem(cartItem: CartItem) {
        val entity = CartEntity(
            id = cartItem.productId,
            productName = cartItem.productName,
            productImage = cartItem.productImage,
            quantity = cartItem.quantity,
            unitPrice = cartItem.unitPrice
        )
        db.getQuickMartDao().deleteItem(entity)
        cartItems = cartItems.toMutableList().apply {
            removeAll { it.productId == cartItem.productId }
        }
    }

    suspend fun getCartTotal(): Double {
        return db.getQuickMartDao().getCartTotal() ?: 0.00
    }

    suspend fun getAllCartItems() {
        cartItems = db.getQuickMartDao().getAllCartItems().map {
            CartItem(
                productId = it.id,
                productName = it.productName,
                productImage = it.productImage,
                quantity = it.quantity,
                unitPrice = it.unitPrice
            )
        }
    }
}