package com.example.quickmart.ui.products

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmart.data.repository.CartRepository
import com.example.quickmart.data.repository.ProductRepository
import com.example.quickmart.models.CartItem
import com.example.quickmart.models.Category
import com.example.quickmart.models.Product
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {
    private var productsRegistration: ListenerRegistration? = null
    private var categoriesRegistration: ListenerRegistration? = null
    var searchQuery by mutableStateOf("")
    var selectedCategory by mutableStateOf("fMnacTlx7mNYzw6wcJhW")
    var productsCategory by mutableStateOf(listOf<Category>())
    var productsList by mutableStateOf(listOf<Product>())

    init {
        initProducts()
        initCategories()
    }

    override fun onCleared() {
        productsRegistration?.remove()
        categoriesRegistration?.remove()
        super.onCleared()
    }

    private fun initProducts() {
        productsRegistration = ProductRepository.getProducts { updatedProducts ->
            productsList = updatedProducts
        }
    }

    private fun initCategories() {
        categoriesRegistration = ProductRepository.getCategories { updatedCategories ->
            productsCategory = updatedCategories
        }
    }

    fun searchForProduct() {
        viewModelScope.launch {
            productsList = ProductRepository.searchForProduct(
                searchQuery,
                selectedCategory
            )

        }
    }

    fun addItemToCart(product: Product) {
        viewModelScope.launch {
            val mapper = CartItem(
                productId = product.id,
                productName = product.title,
                productImage = product.imageUrl,
                quantity = 1,
                unitPrice = product.price
            )
            CartRepository.addItem(mapper)
        }
    }
}