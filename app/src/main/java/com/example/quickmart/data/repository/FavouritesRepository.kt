package com.example.quickmart.data.repository

import com.example.quickmart.data.db.QuickMartDatabase
import com.example.quickmart.data.db.entities.FavouriteEntity
import com.example.quickmart.models.FavouriteItem

object FavouritesRepository {
    private lateinit var db: QuickMartDatabase

    fun initDb(database: QuickMartDatabase) {
        db = database
    }

    suspend fun addItem(favouriteItem: FavouriteItem) {
        val entity = FavouriteEntity(
            id = favouriteItem.productId,
            productName = favouriteItem.productName,
            productImage = favouriteItem.productImage,
            unitPrice = favouriteItem.unitPrice
        )
        db.getQuickMartDao().addItemToFavourite(entity)
    }

    suspend fun deleteItem(favouriteItem: FavouriteItem) {
        val entity = FavouriteEntity(
            id = favouriteItem.productId,
            productName = favouriteItem.productName,
            productImage = favouriteItem.productImage,
            unitPrice = favouriteItem.unitPrice
        )
        db.getQuickMartDao().deleteItemFromFavourite(entity)
    }

    suspend fun getAllFavouritesItems(): List<FavouriteItem> {
        return db.getQuickMartDao().getAllFavouriteItems().map {
            FavouriteItem(
                productId = it.id,
                productName = it.productName,
                productImage = it.productImage,
                unitPrice = it.unitPrice
            )
        }
    }

    suspend fun isInFavourite(id: String): Boolean {
        return db.getQuickMartDao().getFavouriteItemById(id) != null
    }
}