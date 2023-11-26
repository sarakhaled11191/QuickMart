package com.example.quickmart.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.quickmart.data.db.entities.CartEntity
import com.example.quickmart.data.db.entities.CartItemDetails
import com.example.quickmart.data.db.entities.CategoryEntity
import com.example.quickmart.data.db.entities.FavouriteEntity
import com.example.quickmart.data.db.entities.FavouriteItemDetails
import com.example.quickmart.data.db.entities.ProductEntity
import com.example.quickmart.data.db.entities.ProductWithCategory
import com.example.quickmart.data.db.entities.UserEntity

@Dao
interface QuickMartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItemToCart(cartEntity: CartEntity)

    @Query("DELETE FROM carts WHERE productId = :productId")
    suspend fun deleteItemFromCart(productId: String)

    @Query("UPDATE carts SET quantity = :quantity WHERE productId = :productId")
    suspend fun updateItemInCart(productId: String, quantity: Int)

    @Transaction
    @Query("SELECT * FROM carts")
    suspend fun getAllCartItems(): List<CartItemDetails>

    @Query("SELECT SUM(carts.quantity * products.price) FROM carts INNER JOIN products ON carts.productId = products.id")
    suspend fun getCartTotal(): Double?

    @Query("DELETE FROM carts")
    suspend fun clearCart()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItemToFavourite(favouriteEntity: FavouriteEntity)

    @Query("DELETE FROM favourites WHERE productId = :productId")
    suspend fun deleteItemFromFavourite(productId: String)

    @Query("SELECT * FROM favourites")
    suspend fun getAllFavouriteItems(): List<FavouriteItemDetails>

    @Query("SELECT * FROM favourites WHERE productId = :itemId")
    suspend fun getFavouriteItemById(itemId: String): FavouriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(categoryEntity: CategoryEntity)

    @Query("DELETE FROM categories WHERE id = :categoryId")
    suspend fun deleteCategoryById(categoryId: String)

    @Query("SELECT * FROM categories WHERE category = :categoryName")
    suspend fun getCategoryByName(categoryName: String): CategoryEntity?

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(productEntity: ProductEntity)

    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteProductById(productId: String)

    @Query("UPDATE products SET rating = :rating WHERE id = :productId")
    suspend fun updateRatingOfProduct(productId: String, rating: Int)

    @Transaction
    @Query("SELECT products.*, categories.category FROM products INNER JOIN categories ON products.categoryId = categories.id WHERE (:name = '' OR title LIKE '%' || :name || '%') AND (:categoryParam = 'All' OR categories.category = :categoryParam)")
    suspend fun getProducts(name: String, categoryParam: String = "All"): List<ProductWithCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userEntity: UserEntity)

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getUser(): UserEntity

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: String)
}