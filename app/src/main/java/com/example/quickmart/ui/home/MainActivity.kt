package com.example.quickmart.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quickmart.data.repository.CartRepository
import com.example.quickmart.ui.cart.CartScreen
import com.example.quickmart.ui.home.navigation.BottomBarDestination
import com.example.quickmart.ui.home.navigation.currentRoute
import com.example.quickmart.ui.product.ProductScreen
import com.example.quickmart.ui.theme.H1Color
import com.example.quickmart.ui.theme.QuickMartTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickMartTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            val currentRoute = currentRoute(navController)
                            BottomBarDestination.values().forEach { destination ->
                                NavigationBarItem(
                                    selected = currentRoute == destination.route,
                                    onClick = {
                                        navController.navigate(destination.route)
                                    },
                                    label = { Text(text = destination.toString()) },
                                    icon = {
                                        Icon(
                                            destination.icon,
                                            contentDescription = "Shop",
                                            tint = H1Color
                                        )
                                    }
                                )
                            }
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(it)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = BottomBarDestination.Shop.route
                        ) {
                            composable(BottomBarDestination.Shop.route) {
                                ProductScreen()
                            }
                            composable(BottomBarDestination.Cart.route) {
                                CartScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
