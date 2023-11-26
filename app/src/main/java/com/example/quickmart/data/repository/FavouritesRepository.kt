package com.example.quickmart.data.repository

import com.example.quickmart.data.db.QuickMartDatabase
import com.example.quickmart.data.db.entities.FavouriteEntity
import com.example.quickmart.models.FavouriteItem
import com.example.quickmart.utils.appUser

object FavouritesRepository {
    private lateinit var db: QuickMartDatabase

    fun initDb(database: QuickMartDatabase) {
        db = database
    }

    suspend fun addItem(favouriteItem: FavouriteItem) {
        val entity = FavouriteEntity(
            productId = favouriteItem.productId,
            userId = appUser!!.id
        )
        db.getQuickMartDao().addItemToFavourite(entity)
    }

    suspend fun deleteItem(favouriteItem: FavouriteItem) {
        db.getQuickMartDao().deleteItemFromFavourite(favouriteItem.productId)
    }

    suspend fun getAllFavouritesItems(): List<FavouriteItem> {
        return db.getQuickMartDao().getAllFavouriteItems().map {
            FavouriteItem(
                productId = it.productEntity.id,
                productName = it.productEntity.title,
                productImage = it.productEntity.imageName,
                unitPrice = it.productEntity.price
            )
        }
    }

    suspend fun isInFavourite(id: String): Boolean {
        return db.getQuickMartDao().getFavouriteItemById(id) != null
    }
}