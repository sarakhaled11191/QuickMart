package com.example.quickmart.ui.favourites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmart.data.db.QuickMartDatabase
import com.example.quickmart.data.repository.CartRepository
import com.example.quickmart.data.repository.FavouritesRepository
import com.example.quickmart.models.CartItem
import com.example.quickmart.models.FavouriteItem
import kotlinx.coroutines.launch

class FavouritesViewModel : ViewModel() {
    var favouritesItems by mutableStateOf(listOf<FavouriteItem>())

    fun loadItems() {
        viewModelScope.launch {
            favouritesItems = FavouritesRepository.getAllFavouritesItems()
        }
    }

    fun deleteItem(favouriteItem: FavouriteItem) {
        viewModelScope.launch {
            FavouritesRepository.deleteItem(favouriteItem)
            favouritesItems = favouritesItems.filter { it.productId != favouriteItem.productId }
        }
    }

    fun addAllItemsToCart() {
        viewModelScope.launch {
            favouritesItems.map {
                CartRepository.addItem(
                    CartItem(
                        productId = it.productId,
                        productName = it.productName,
                        productImage = it.productImage,
                        quantity = 1,
                        unitPrice = it.unitPrice
                    )
                )
                FavouritesRepository.deleteItem(it)
            }
            favouritesItems = emptyList()
        }
    }
}