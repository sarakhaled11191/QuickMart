package com.example.quickmart.ui.cart

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickmart.data.db.QuickMartDatabase
import com.example.quickmart.data.repository.CartRepository
import com.example.quickmart.models.CartItem
import com.example.quickmart.ui.components.CartItemUi
import com.example.quickmart.ui.theme.GreenColor
import com.example.quickmart.ui.theme.H1Color
import kotlinx.coroutines.launch

@Composable
fun CartScreen() {
    val context = LocalContext.current
    val dataBase = QuickMartDatabase(context)
    val repository = CartRepository(dataBase)
    val coroutineScope = rememberCoroutineScope()
    var totalPrice by remember { mutableDoubleStateOf(0.0) }
    var items by remember { mutableStateOf(listOf<CartItem>()) }
    LaunchedEffect(true) {
        repository.getAllCartItems()
        items = repository.cartItems
        totalPrice = repository.getCartTotal()
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
                items(items) {
                    CartItemUi(cartItem = it, onDeleteIconClicked = { cartItem ->
                        coroutineScope.launch {
                            repository.deleteItem(cartItem)
                            items = items.toMutableList().apply {
                                removeAll { it.productId == cartItem.productId }
                            }
                            totalPrice -= (cartItem.unitPrice * cartItem.quantity)
                            Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show()
                        }
                    }, onPriceChange = { diff ->
                        totalPrice += diff
                    })
                }
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 10.dp)
                .background(GreenColor, RoundedCornerShape(15.dp))
                .align(BottomCenter)
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
                text = '$' + String.format("%.2f", totalPrice),
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
            )

        }
    }
}

