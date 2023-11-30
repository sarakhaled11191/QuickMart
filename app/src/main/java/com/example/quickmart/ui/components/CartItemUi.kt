package com.example.quickmart.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.quickmart.models.CartItem
import com.example.quickmart.ui.theme.H1Color

@Composable
fun CartItemUi(
    cartItem: CartItem,
    onDeleteIconClicked: (CartItem) -> Unit,
    onPriceChange: (Double) -> Unit,
    updateCartQuantity: (Int) -> Unit
) {
    var totalPrice by remember { mutableDoubleStateOf(cartItem.calculateTotalPrice()) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(15.dp),
    ) {
        Column(
            modifier = Modifier.wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                AsyncImage(
                    model = cartItem.productImage,
                    contentDescription = "productImage",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(70.dp)
                )
                Column(
                    modifier = Modifier
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = cartItem.productName,
                                color = H1Color,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                            )
                        }
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = "delete item",
                            tint = Color.Gray.copy(0.7f),
                            modifier = Modifier.clickable {
                                onDeleteIconClicked(cartItem)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CounterView(
                            modifier = Modifier, quantityP = cartItem.quantity,
                            onPlusButtonClicked = {
                                val previousPrice = cartItem.calculateTotalPrice()
                                cartItem.quantity += 1
                                updateCartQuantity(cartItem.quantity)
                                totalPrice = cartItem.calculateTotalPrice()
                                val newPrice = cartItem.calculateTotalPrice()
                                val priceDifference = newPrice - previousPrice
                                onPriceChange(priceDifference)
                            },
                            onMiensButtonClicked = {
                                val previousPrice = cartItem.calculateTotalPrice()
                                cartItem.quantity -= 1
                                updateCartQuantity(cartItem.quantity)
                                totalPrice = cartItem.calculateTotalPrice()
                                val newPrice = cartItem.calculateTotalPrice()
                                val priceDifference = newPrice - previousPrice
                                onPriceChange(priceDifference)
                            }
                        )
                        Text(
                            text = "$${String.format("%.2f", totalPrice)}",
                            color = H1Color,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = Color.Gray.copy(0.19f))
        }
    }
}