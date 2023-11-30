package com.example.quickmart.data.repository

import com.example.quickmart.models.CartItem
import com.example.quickmart.models.FavouriteItem
import com.example.quickmart.models.Product
import com.example.quickmart.utils.appUser
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

object FavouritesRepository {
    private val firebaseFireStore = Firebase.firestore

    suspend fun addItem(favouriteItem: FavouriteItem) {
        try {
            val querySnapshot = firebaseFireStore.collection("favourite")
                .whereEqualTo("productId", favouriteItem.productId)
                .whereEqualTo("userId", appUser!!.id)
                .get()
                .await()
            if (querySnapshot.isEmpty) {
                val favouriteItemData = hashMapOf(
                    "productId" to favouriteItem.productId,
                    "userId" to appUser!!.id,
                )
                val documentReference =
                    firebaseFireStore.collection("favourite").add(favouriteItemData).await()
                documentReference.update("id", documentReference.id).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteItem(favouriteItem: FavouriteItem) {
        try {
            val querySnapshot = firebaseFireStore.collection("favourite")
                .whereEqualTo("productId", favouriteItem.productId)
                .whereEqualTo("userId", appUser!!.id)
                .get()
                .await()
            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents[0]
                document.reference.delete().await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getAllFavouritesItems(): List<FavouriteItem> {
        val favouriteItems = mutableListOf<FavouriteItem>()
        try {
            val querySnapshot = firebaseFireStore.collection("favourite")
                .whereEqualTo("userId", appUser!!.id)
                .get()
                .await()
            for (document in querySnapshot.documents) {
                val productId = document["productId"] as String
                val productSnapshot = firebaseFireStore.collection("products")
                    .document(productId)
                    .get()
                    .await()
                if (productSnapshot.exists()) {
                    val product = productSnapshot.toObject(Product::class.java)
                    if (product != null) {
                        val favouriteItem = FavouriteItem(
                            productId,
                            product.title,
                            product.imageUrl,
                            product.price
                        )
                        favouriteItems.add(favouriteItem)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return favouriteItems
    }
}