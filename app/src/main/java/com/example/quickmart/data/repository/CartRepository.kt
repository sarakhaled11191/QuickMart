package com.example.quickmart.data.repository

import com.example.quickmart.models.CartItem
import com.example.quickmart.models.Product
import com.example.quickmart.utils.appUser
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

object CartRepository {
    private val firebaseFireStore = Firebase.firestore

    suspend fun addItem(cartItem: CartItem) {
        try {
            val querySnapshot = firebaseFireStore.collection("cart")
                .whereEqualTo("productId", cartItem.productId)
                .whereEqualTo("userId", appUser!!.id)
                .get()
                .await()
            if (querySnapshot.isEmpty) {
                val cartItemData = hashMapOf(
                    "productId" to cartItem.productId,
                    "userId" to appUser!!.id,
                    "quantity" to cartItem.quantity
                )
                val documentReference =
                    firebaseFireStore.collection("cart").add(cartItemData).await()
                documentReference.update("id", documentReference.id).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateItem(productId: String, quantity: Int) {
        try {
            val querySnapshot = firebaseFireStore.collection("cart")
                .whereEqualTo("productId", productId)
                .whereEqualTo("userId", appUser!!.id)
                .get()
                .await()
            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents[0]
                document.reference.update("quantity", quantity).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteItem(cartItem: CartItem) {
        try {
            val querySnapshot = firebaseFireStore.collection("cart")
                .whereEqualTo("productId", cartItem.productId)
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

    suspend fun getCartTotal(): Double {
        var total = 0.0
        try {
            val querySnapshot = firebaseFireStore.collection("cart")
                .whereEqualTo("userId", appUser!!.id)
                .get()
                .await()
            for (document in querySnapshot.documents) {
                val quantity = document["quantity"] as Long
                val productId = document["productId"] as String
                val productSnapshot = firebaseFireStore.collection("products")
                    .document(productId)
                    .get()
                    .await()

                if (productSnapshot.exists()) {
                    val price = productSnapshot["price"] as Double
                    total += price * quantity
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return total
    }

    suspend fun getAllCartItems(): List<CartItem> {
        val cartItems = mutableListOf<CartItem>()
        try {
            val querySnapshot = firebaseFireStore.collection("cart")
                .whereEqualTo("userId", appUser!!.id)
                .get()
                .await()
            for (document in querySnapshot.documents) {
                val productId = document["productId"] as String
                val quantity = (document["quantity"] as Long).toInt()
                val productSnapshot = firebaseFireStore.collection("products")
                    .document(productId)
                    .get()
                    .await()
                if (productSnapshot.exists()) {
                    val product = productSnapshot.toObject(Product::class.java)
                    if (product != null) {
                        val cartItem = CartItem(
                            productId,
                            product.title,
                            product.imageUrl,
                            quantity,
                            product.price
                        )
                        cartItems.add(cartItem)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return cartItems
    }

    suspend fun clearCart() {
        try {
            val querySnapshot = firebaseFireStore.collection("cart")
                .whereEqualTo("userId", appUser!!.id)
                .get()
                .await()

            for (document in querySnapshot.documents) {
                document.reference.delete().await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}