package com.example.quickmart.ui.home.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomBarDestination(
    val icon: ImageVector,
    val route: String
) {
    Shop(Icons.Outlined.DateRange, "shop"),
    Cart(Icons.Outlined.ShoppingCart, "cart"),
    Favourites(Icons.Outlined.FavoriteBorder, "favourites"),
    Product(Icons.Outlined.Send, "product"),
}

