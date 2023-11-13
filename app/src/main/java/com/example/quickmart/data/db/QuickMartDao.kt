package com.example.quickmart.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quickmart.data.db.entities.CartEntity
import com.example.quickmart.data.db.entities.FavouriteEntity

@Dao
interface QuickMartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItemToCart(cartEntity: CartEntity)

    @Delete
    suspend fun deleteItemFromCart(cartEntity: CartEntity)

    @Query("UPDATE CartEntity SET quantity = :quantity WHERE id = :productId")
    suspend fun updateItemInCart(productId: String, quantity: Int)

    @Query("SELECT * FROM CartEntity")
    suspend fun getAllCartItems(): List<CartEntity>

    @Query("SELECT SUM(quantity * unitPrice) FROM CartEntity")
    suspend fun getCartTotal(): Double?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItemToFavourite(favouriteEntity: FavouriteEntity)
    @Delete
    suspend fun deleteItemFromFavourite(favouriteEntity: FavouriteEntity)

    @Query("SELECT * FROM FavouriteEntity")
    suspend fun getAllFavouriteItems(): List<FavouriteEntity>

    @Query("SELECT * FROM FavouriteEntity WHERE id = :itemId")
    suspend fun getFavouriteItemById(itemId: String): FavouriteEntity?
}