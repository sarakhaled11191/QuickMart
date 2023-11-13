package com.example.quickmart.data.repository

import com.example.quickmart.data.db.QuickMartDatabase
import com.example.quickmart.data.db.entities.CartEntity
import com.example.quickmart.models.CartItem

object CartRepository {
    private lateinit var db: QuickMartDatabase

    fun initDb(database: QuickMartDatabase) {
        db = database
    }

    suspend fun addItem(cartItem: CartItem) {
        val entity = CartEntity(
            id = cartItem.productId,
            productName = cartItem.productName,
            productImage = cartItem.productImage,
            quantity = cartItem.quantity,
            unitPrice = cartItem.unitPrice
        )
        db.getQuickMartDao().addItemToCart(entity)
    }

    suspend fun updateItem(productId: String, quantity: Int) {
        db.getQuickMartDao().updateItemInCart(productId, quantity)
    }

    suspend fun deleteItem(cartItem: CartItem) {
        val entity = CartEntity(
            id = cartItem.productId,
            productName = cartItem.productName,
            productImage = cartItem.productImage,
            quantity = cartItem.quantity,
            unitPrice = cartItem.unitPrice
        )
        db.getQuickMartDao().deleteItemFromCart(entity)
    }

    suspend fun getCartTotal(): Double {
        return db.getQuickMartDao().getCartTotal() ?: 0.00
    }

    suspend fun getAllCartItems(): List<CartItem> {
        return db.getQuickMartDao().getAllCartItems().map {
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