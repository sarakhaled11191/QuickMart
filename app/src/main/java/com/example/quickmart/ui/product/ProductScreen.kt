package com.example.quickmart.ui.product

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.quickmart.data.db.QuickMartDatabase
import com.example.quickmart.data.repository.CartRepository
import com.example.quickmart.data.repository.ProductRepository
import com.example.quickmart.models.CartItem
import com.example.quickmart.models.Product
import com.example.quickmart.ui.components.DropDownMenu
import com.example.quickmart.ui.components.ProductItem
import com.example.quickmart.ui.components.SearchBarWithClearIcon
import com.example.quickmart.ui.theme.GrayColor
import kotlinx.coroutines.launch

@Composable
fun ProductScreen() {
    val context = LocalContext.current
    val dataBase = QuickMartDatabase(context)
    val repository = CartRepository(dataBase)
    val coroutineScope = rememberCoroutineScope()
    var selectedCategory by remember { mutableStateOf("All") }
    LaunchedEffect(true) {
        ProductRepository.initProducts(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            SearchBarWithClearIcon(
                modifier = Modifier
                    .background(GrayColor, shape = RoundedCornerShape(15.dp))
                    .padding(10.dp)
                    .weight(1f)
            ) {
                ProductRepository.getProducts(it, selectedCategory)
            }
            DropDownMenu(
                ProductRepository.getProductCategories(context)
            ) {
                selectedCategory = it
                ProductRepository.getProducts("", selectedCategory)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            items(ProductRepository.productList) {
                Column(
                    modifier = Modifier.padding(5.dp)
                ) {
                    ProductItem(product = it) {
                        coroutineScope.launch {
                            repository.addItem(
                                CartItem(
                                    productId = it.id,
                                    productName = it.title,
                                    productImage = it.imageName,
                                    quantity = 1,
                                    unitPrice = it.price
                                )
                            )
                            Toast.makeText(context, "Item Added", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}