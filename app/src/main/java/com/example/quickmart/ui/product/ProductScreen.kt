package com.example.quickmart.ui.product

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.quickmart.ui.components.CounterView
import com.example.quickmart.ui.theme.GreenColor
import com.example.quickmart.ui.theme.H1Color
import com.example.quickmart.ui.theme.StarColor

@Composable
fun ProductScreen(viewModel: ProductViewModel, navController: NavHostController) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopStart)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
            ) {
                AsyncImage(
                    model = viewModel.currentProduct?.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .size(300.dp)
                )
                Surface(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(top = 15.dp, start = 10.dp),
                    shape = CircleShape,
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = viewModel.currentProduct?.title ?: "",
                        color = H1Color,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                    )
                }
                if(viewModel.isInFavourites){
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "delete item",
                        tint = Color.Gray.copy(0.7f),
                        modifier = Modifier.clickable {
                            viewModel.isInFavourites = false
                            viewModel.deleteItemFromFavourite()
                            Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show()
                        }
                    )
                }else{
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "add item",
                        tint = Color.Gray.copy(0.7f),
                        modifier = Modifier.clickable {
                            viewModel.isInFavourites = true
                            viewModel.addItemToFavourite()
                            Toast.makeText(context, "Item Added", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CounterView(
                    modifier = Modifier, quantityP = 1,
                    onPlusButtonClicked = {
                        viewModel.quantity++
                        viewModel.totalPrice += viewModel.currentProduct?.price ?: 0.0
                    },
                    onMiensButtonClicked = {
                        viewModel.quantity--
                        viewModel.totalPrice -= viewModel.currentProduct?.price ?: 0.0
                    }
                )
                Text(
                    text = "$${String.format("%.2f", viewModel.totalPrice)}",
                    color = H1Color,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Divider(color = Color.Gray.copy(0.19f))
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ) {
                        expanded = !expanded
                    }
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Product Details",
                            color = H1Color,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = H1Color
                        )
                    }
                    if (expanded) {
                        Text(
                            text = viewModel.currentProduct?.description ?: "",
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Color.Gray.copy(0.19f))
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Review",
                    color = H1Color,
                    fontWeight = FontWeight.Bold,
                )
                Row {
                    for (i in 0 until 5) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (i < (viewModel.currentProduct?.rating ?: 0)) StarColor else Color.Gray
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 10.dp)
                .background(GreenColor, RoundedCornerShape(15.dp))
                .align(Alignment.BottomCenter)
                .size(width = 0.dp, height = 70.dp)
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource()
                ) {
                    viewModel.addItemToBasket()
                    Toast.makeText(context, "Item Added", Toast.LENGTH_SHORT).show()
                }
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "Add To Basket",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.White,
            )
        }
    }
}