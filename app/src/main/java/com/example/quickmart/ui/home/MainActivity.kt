package com.example.quickmart.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import com.example.quickmart.data.db.QuickMartDatabase
import com.example.quickmart.data.repository.AuthenticationRepository
import com.example.quickmart.ui.cart.CartScreen
import com.example.quickmart.ui.cart.CartViewModel
import com.example.quickmart.ui.cart.Checkout
import com.example.quickmart.ui.favourites.FavouritesScreen
import com.example.quickmart.ui.favourites.FavouritesViewModel
import com.example.quickmart.ui.home.navigation.BottomBarDestination
import com.example.quickmart.ui.home.navigation.currentRoute
import com.example.quickmart.ui.login.LoginScreen
import com.example.quickmart.ui.login.LoginViewModel
import com.example.quickmart.ui.product.ProductScreen
import com.example.quickmart.ui.product.ProductViewModel
import com.example.quickmart.ui.products.ProductsScreen
import com.example.quickmart.ui.products.ProductsViewModel
import com.example.quickmart.ui.register.RegisterScreen
import com.example.quickmart.ui.register.RegisterViewModel
import com.example.quickmart.ui.theme.H1Color
import com.example.quickmart.ui.theme.QuickMartTheme
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuickMartTheme {
                val navController = rememberNavController()
                val db = QuickMartDatabase(this)
                var isLoggedIn: Boolean
                runBlocking {
                    isLoggedIn = AuthenticationRepository.isLoggedIn(db.getQuickMartDao())
                }
                val startDestination = if (isLoggedIn) {
                    BottomBarDestination.Shop.route
                } else {
                    BottomBarDestination.Login.route
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val currentRoute = currentRoute(navController)
                        if (currentRoute !in listOf(
                                BottomBarDestination.Login.route,
                                BottomBarDestination.Register.route
                            )
                        ) {
                            NavigationBar {
                                BottomBarDestination.values().forEach { destination ->
                                    if (destination != BottomBarDestination.Product &&
                                        destination != BottomBarDestination.Checkout &&
                                        destination != BottomBarDestination.Login &&
                                        destination != BottomBarDestination.Register
                                    ) {
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
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier.padding(it)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = startDestination
                        ) {
                            composable(BottomBarDestination.Shop.route) {
                                val viewModel: ProductsViewModel by viewModels()
                                ProductsScreen(viewModel, navController)
                            }
                            composable(BottomBarDestination.Cart.route) {
                                val viewModel: CartViewModel by viewModels()
                                CartScreen(viewModel, navController)
                            }
                            composable(BottomBarDestination.Favourites.route) {
                                val viewModel: FavouritesViewModel by viewModels()
                                FavouritesScreen(viewModel)
                            }
                            composable("${BottomBarDestination.Product.route}/{id}") {
                                val productId = it.arguments?.getString("id") ?: ""
                                val viewModel: ProductViewModel by viewModels()
                                viewModel.getItem(productId)
                                ProductScreen(viewModel, navController)
                            }
                            composable(BottomBarDestination.Checkout.route) {
                                Checkout {
                                    navController.navigate(BottomBarDestination.Shop.route)
                                }
                            }
                            composable(BottomBarDestination.Register.route) {
                                val viewModel: RegisterViewModel by viewModels()
                                RegisterScreen(viewModel, navController)
                            }
                            composable(BottomBarDestination.Login.route) {
                                val viewModel: LoginViewModel by viewModels()
                                viewModel.initDao(db.getQuickMartDao())
                                LoginScreen(viewModel, navController)
                            }
                        }
                    }
                }
            }
        }
    }
}
