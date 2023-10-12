package com.example.quickmart.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quickmart.data.db.entities.CartEntity
import com.example.quickmart.models.CartItem

@Dao
interface QuickMartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(cartEntity: CartEntity)

    @Delete
    suspend fun deleteItem(cartEntity: CartEntity)

    @Query("UPDATE CartEntity SET quantity = :quantity WHERE id = :productId")
    suspend fun updateItem(productId: String, quantity: Int)

    @Query("SELECT * FROM CartEntity")
    suspend fun getAllCartItems(): List<CartEntity>

    @Query("SELECT SUM(quantity * unitPrice) FROM CartEntity")
    suspend fun getCartTotal(): Double?
}