package com.example.quickmart.ui.favourites

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
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickmart.ui.components.FavouriteItemUi
import com.example.quickmart.ui.theme.GreenColor
import com.example.quickmart.ui.theme.H1Color

@Composable
fun FavouritesScreen(viewModel: FavouritesViewModel) {
    LaunchedEffect(true) {
        viewModel.loadItems()
    }
    val isFavouritesEmpty = remember {
        derivedStateOf {
            viewModel.favouritesItems.isEmpty()
        }
    }
    val isButtonEnabled = remember {
        derivedStateOf {
            !isFavouritesEmpty.value
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopStart)
        ) {
            Text(
                text = "My Favourites",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = H1Color,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 20.dp)
            )
            Divider(color = Color.Gray.copy(0.19f))
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn {
                if (viewModel.favouritesItems.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(70.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Your favourites is empty",
                                textAlign = TextAlign.Center,
                                style = TextStyle(fontSize = 18.sp, color = Color.Gray)
                            )
                        }
                    }
                } else {
                    items(viewModel.favouritesItems) { favouriteItem ->
                        FavouriteItemUi(
                            favouriteItem = favouriteItem,
                            onDeleteIconClicked = {
                                viewModel.deleteItem(it)
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
                .background(
                    if (isButtonEnabled.value) GreenColor else Color.Gray,
                    shape = RoundedCornerShape(15.dp)
                )
                .align(Alignment.BottomCenter)
                .size(width = 0.dp, height = 70.dp)
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource(),
                    enabled = isButtonEnabled.value
                ) {
                    viewModel.addAllItemsToCart()
                }
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "Move All To Cart",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.White,
            )
        }
    }
}