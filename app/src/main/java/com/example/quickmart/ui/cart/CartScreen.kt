package com.example.quickmart.ui.cart

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.quickmart.ui.components.CartItemUi
import com.example.quickmart.ui.home.navigation.BottomBarDestination
import com.example.quickmart.ui.theme.GreenColor
import com.example.quickmart.ui.theme.H1Color

@Composable
fun CartScreen(viewModel: CartViewModel, navController: NavHostController) {
    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.initView()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(TopStart)
        ) {
            Text(
                text = "My Cart",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = H1Color,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(vertical = 20.dp)
            )
            Divider(color = Color.Gray.copy(0.19f))
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn {
                if (viewModel.cartItems.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ShoppingCart,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(70.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Your cart is empty",
                                textAlign = TextAlign.Center,
                                style = TextStyle(fontSize = 18.sp, color = Color.Gray)
                            )
                        }
                    }
                } else {
                    items(viewModel.cartItems) { cartItem ->
                        CartItemUi(
                            cartItem = cartItem,
                            onDeleteIconClicked = {
                                viewModel.deleteItem(cartItem)
                                Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show()
                            },
                            onPriceChange = { diff ->
                                viewModel.totalPrice += diff
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }


        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 10.dp)
                .background(GreenColor, RoundedCornerShape(15.dp))
                .align(BottomCenter)
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource(),
                ) {
                    viewModel.clearCart()
                    navController.navigate(BottomBarDestination.Checkout.route)
                }
                .size(width = 0.dp, height = 70.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "Go To Checkout",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.White,
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp)
                    .background(Color.Black.copy(0.2f), RoundedCornerShape(5.dp))
                    .padding(5.dp),
                text = '$' + String.format("%.2f", viewModel.totalPrice),
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
            )

        }
    }
}

