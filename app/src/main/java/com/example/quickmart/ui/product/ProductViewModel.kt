package com.example.quickmart.ui.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmart.data.repository.CartRepository
import com.example.quickmart.data.repository.FavouritesRepository
import com.example.quickmart.data.repository.ProductRepository
import com.example.quickmart.models.CartItem
import com.example.quickmart.models.FavouriteItem
import com.example.quickmart.models.Product
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private var productRegistration: ListenerRegistration? = null
    var currentProduct by mutableStateOf<Product?>(null)
    var totalPrice by mutableDoubleStateOf(currentProduct?.price ?: 0.0)
    var isInFavourites by mutableStateOf(false)
    var quantity by mutableIntStateOf(1)

    override fun onCleared() {
        productRegistration?.remove()
        super.onCleared()
    }

    fun getItem(productId: String) {
        productRegistration = ProductRepository.getProductById(productId) { updatedProduct ->
            currentProduct = updatedProduct
            totalPrice = updatedProduct!!.price * quantity
            isInFavourites = updatedProduct.isInFavourites
        }
    }

    fun addItemToBasket() {
        viewModelScope.launch {
            CartRepository.addItem(
                CartItem(
                    productId = currentProduct!!.id,
                    productName = currentProduct!!.title,
                    productImage = currentProduct!!.imageUrl,
                    unitPrice = currentProduct!!.price,
                    quantity = quantity
                )
            )
            quantity = 1
        }
    }

    fun addItemToFavourite() {
        viewModelScope.launch {
            FavouritesRepository.addItem(
                FavouriteItem(
                    productId = currentProduct!!.id,
                    productName = currentProduct!!.title,
                    productImage = currentProduct!!.imageUrl,
                    unitPrice = currentProduct!!.price,
                )
            )
        }
    }

    fun deleteItemFromFavourite() {
        viewModelScope.launch {
            FavouritesRepository.deleteItem(
                FavouriteItem(
                    productId = currentProduct!!.id,
                    productName = currentProduct!!.title,
                    productImage = currentProduct!!.imageUrl,
                    unitPrice = currentProduct!!.price,
                )
            )
        }
    }
}