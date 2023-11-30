package com.example.quickmart.data.repository

import com.example.quickmart.models.Category
import com.example.quickmart.models.Product
import com.example.quickmart.utils.appUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await

object ProductRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val productsCollection = firestore.collection("products")
    private val favouriteCollection = firestore.collection("favourite")
    private val categoriesCollection = firestore.collection("categories")

    fun getProducts(listener: (List<Product>) -> Unit): ListenerRegistration {
        return productsCollection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val products = snapshot.toObjects(Product::class.java)
                listener(products)
            }
        }
    }

    fun getCategories(listener: (List<Category>) -> Unit): ListenerRegistration {
        return categoriesCollection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val categories = snapshot.toObjects(Category::class.java)
                listener(categories)
            }
        }
    }

    suspend fun searchForProduct(
        name: String = "",
        categoryId: String = "fMnacTlx7mNYzw6wcJhW"
    ): List<Product> {
        return try {
            val query = when {
                name.isEmpty() && categoryId == "fMnacTlx7mNYzw6wcJhW" -> productsCollection
                name.isEmpty() -> productsCollection.whereEqualTo("categoryId", categoryId)
                categoryId == "fMnacTlx7mNYzw6wcJhW" -> {
                    productsCollection
                        .orderBy("title")
                        .startAt(name)
                        .endAt(name + "\uf8ff")
                }

                else -> {
                    productsCollection
                        .whereEqualTo("categoryId", categoryId)
                        .orderBy("title")
                        .startAt(name)
                        .endAt(name + "\uf8ff")
                }
            }

            val snapshot = query.get().await()
            snapshot.toObjects(Product::class.java)
                .filter { it.title.lowercase().contains(name.lowercase()) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getProductById(productId: String, listener: (Product?) -> Unit): ListenerRegistration {
        return productsCollection
            .whereEqualTo("id", productId)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val product = snapshot.documents[0].toObject(Product::class.java)
                    if (product != null) {
                        checkIsInFavorites(productId) { isInFavorites ->
                            product.isInFavourites = isInFavorites
                            listener(product)
                        }
                    }
                } else {
                    listener(null)
                }
            }
    }

    private fun checkIsInFavorites(productId: String, callback: (Boolean) -> Unit) {
        favouriteCollection
            .whereEqualTo("productId", productId)
            .whereEqualTo("userId", appUser!!.id!!)
            .get()
            .addOnSuccessListener { documents ->
                callback(!documents.isEmpty)
            }
            .addOnFailureListener {
                callback(false)
            }
    }


}