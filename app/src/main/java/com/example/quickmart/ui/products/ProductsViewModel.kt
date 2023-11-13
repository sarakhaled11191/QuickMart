package com.example.quickmart.ui.products

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmart.data.db.QuickMartDatabase
import com.example.quickmart.data.repository.CartRepository
import com.example.quickmart.data.repository.FavouritesRepository
import com.example.quickmart.data.repository.ProductRepository
import com.example.quickmart.models.CartItem
import com.example.quickmart.models.Product
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProductsViewModel : ViewModel() {
    private val productRepository = ProductRepository
    private val cartRepository = CartRepository
    private val favouritesRepository = FavouritesRepository
    var searchQuery by mutableStateOf("")
    var selectedCategory by mutableStateOf("All")
    var productsCategory by mutableStateOf(listOf<String>())
    var productsList by mutableStateOf(listOf<Product>())

    fun initDatabase(db: QuickMartDatabase) {
        cartRepository.initDb(db)
        favouritesRepository.initDb(db)
    }

    fun loadProducts(context: Context) {
        productRepository.initProducts(context)
        productsList = productRepository.originalProductList
    }

    fun loadCategories(context: Context) {
        productsCategory = productRepository.getProductCategories(context)
    }

    fun getProducts() {
        productsList = ProductRepository.getProducts(
            searchQuery,
            selectedCategory
        )
    }

    fun addItemToCart(product: Product) {
        viewModelScope.launch {
            val mapper = CartItem(
                productId = product.id,
                productName = product.title,
                productImage = product.imageName,
                quantity = 1,
                unitPrice = product.price
            )
            cartRepository.addItem(mapper)
        }
    }

    fun isItemInFavourite(id: String): Boolean {
        var isFavourite: Boolean
        runBlocking {
            isFavourite = favouritesRepository.isInFavourite(id)
        }
        return isFavourite
    }
}