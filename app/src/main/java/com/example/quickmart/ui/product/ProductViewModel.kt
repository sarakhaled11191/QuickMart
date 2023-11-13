package com.example.quickmart.ui.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmart.data.db.QuickMartDatabase
import com.example.quickmart.data.repository.CartRepository
import com.example.quickmart.data.repository.FavouritesRepository
import com.example.quickmart.models.CartItem
import com.example.quickmart.models.FavouriteItem
import com.example.quickmart.models.Product
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val cartRepository = CartRepository
    private val favouriteRepository = FavouritesRepository
    var currentProduct by mutableStateOf<Product?>(null)
    var totalPrice by mutableDoubleStateOf(currentProduct?.price ?: 0.0)
    var isInFavourites by mutableStateOf(currentProduct?.isInFavourites)
    var quantity by mutableIntStateOf(1)

    fun initDatabase(db: QuickMartDatabase) {
        cartRepository.initDb(db)
        favouriteRepository.initDb(db)
    }

    fun setProduct(productP: Product) {
        currentProduct = productP
        totalPrice = currentProduct!!.price
        isInFavourites = currentProduct!!.isInFavourites
    }

    fun addItemToBasket() {
        viewModelScope.launch {
            cartRepository.addItem(
                CartItem(
                    productId = currentProduct!!.id,
                    productName = currentProduct!!.title,
                    productImage = currentProduct!!.imageName,
                    unitPrice = currentProduct!!.price,
                    quantity = quantity
                )
            )
            quantity = 1
        }
    }

    fun addItemToFavourite() {
        viewModelScope.launch {
            favouriteRepository.addItem(
                FavouriteItem(
                    productId = currentProduct!!.id,
                    productName = currentProduct!!.title,
                    productImage = currentProduct!!.imageName,
                    unitPrice = currentProduct!!.price,
                )
            )
        }
    }

    fun deleteItemFromFavourite() {
        viewModelScope.launch {
            favouriteRepository.deleteItem(
                FavouriteItem(
                    productId = currentProduct!!.id,
                    productName = currentProduct!!.title,
                    productImage = currentProduct!!.imageName,
                    unitPrice = currentProduct!!.price,
                )
            )
        }
    }
}