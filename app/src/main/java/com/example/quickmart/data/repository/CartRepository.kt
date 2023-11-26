package com.example.quickmart.data.repository

import android.util.Log
import com.example.quickmart.data.db.QuickMartDatabase
import com.example.quickmart.data.db.entities.CartEntity
import com.example.quickmart.models.CartItem
import com.example.quickmart.utils.appUser

object CartRepository {
    private lateinit var db: QuickMartDatabase

    fun initDb(database: QuickMartDatabase) {
        db = database
    }

    suspend fun addItem(cartItem: CartItem) {
        val entity = CartEntity(
            productId = cartItem.productId,
            quantity = cartItem.quantity,
            userId = appUser!!.id
        )
        db.getQuickMartDao().addItemToCart(entity)
    }

    suspend fun updateItem(productId: String, quantity: Int) {
        db.getQuickMartDao().updateItemInCart(productId, quantity)
    }

    suspend fun deleteItem(cartItem: CartItem) {
        db.getQuickMartDao().deleteItemFromCart(cartItem.productId)
    }

    suspend fun getCartTotal(): Double {
        return db.getQuickMartDao().getCartTotal() ?: 0.00
    }

    suspend fun getAllCartItems(): List<CartItem> {
        return db.getQuickMartDao().getAllCartItems().map {
            CartItem(
                productId = it.cartEntity.productId,
                productName = it.productEntity.title,
                productImage = it.productEntity.imageName,
                quantity = it.cartEntity.quantity,
                unitPrice = it.productEntity.price
            )
        }
    }

    suspend fun clearCart(){
        db.getQuickMartDao().clearCart()
    }
}