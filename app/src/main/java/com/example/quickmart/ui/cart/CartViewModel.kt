package com.example.quickmart.ui.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmart.data.db.QuickMartDatabase
import com.example.quickmart.data.repository.CartRepository
import com.example.quickmart.models.CartItem
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val cartRepository = CartRepository
    var totalPrice by mutableDoubleStateOf(0.0)
    var cartItems by mutableStateOf(listOf<CartItem>())
    fun initDatabase(db: QuickMartDatabase) {
        cartRepository.initDb(db)
    }

    fun initView() {
        viewModelScope.launch {
            cartItems = cartRepository.getAllCartItems()
            totalPrice = cartRepository.getCartTotal()
        }
    }

    fun deleteItem(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.deleteItem(cartItem)
            cartItems = cartItems.filter { it.productId != cartItem.productId }
            totalPrice -= (cartItem.unitPrice * cartItem.quantity)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }
}