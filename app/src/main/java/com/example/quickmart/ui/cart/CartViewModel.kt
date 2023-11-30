package com.example.quickmart.ui.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmart.data.repository.CartRepository
import com.example.quickmart.models.CartItem
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    var totalPrice by mutableDoubleStateOf(0.0)
    var cartItems by mutableStateOf(listOf<CartItem>())


    fun initView() {
        viewModelScope.launch {
            cartItems = CartRepository.getAllCartItems()
            totalPrice = CartRepository.getCartTotal()
        }
    }

    fun deleteItem(cartItem: CartItem) {
        viewModelScope.launch {
            CartRepository.deleteItem(cartItem)
            cartItems = cartItems.filter { it.productId != cartItem.productId }
            totalPrice -= (cartItem.unitPrice * cartItem.quantity)
        }
    }

    fun updateItem(productId: String, quantity: Int) {
        viewModelScope.launch {
            CartRepository.updateItem(productId, quantity)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            CartRepository.clearCart()
        }
    }
}