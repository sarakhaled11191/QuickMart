package com.example.quickmart.ui.products

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.quickmart.ui.components.DropDownMenu
import com.example.quickmart.ui.components.ProductItem
import com.example.quickmart.ui.components.SearchBarWithClearIcon
import com.example.quickmart.ui.home.navigation.BottomBarDestination
import com.example.quickmart.ui.theme.GrayColor

@Composable
fun ProductsScreen(viewModel: ProductsViewModel, navController: NavHostController) {
    val context = LocalContext.current
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
                viewModel.searchQuery = it
                viewModel.searchForProduct()
            }
            DropDownMenu(
                viewModel.productsCategory
            ) {
                viewModel.selectedCategory = it
                viewModel.searchForProduct()
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            items(viewModel.productsList) {
                Column(
                    modifier = Modifier.padding(5.dp)
                ) {
                    ProductItem(
                        product = it,
                        onAddClick = {
                            viewModel.addItemToCart(it)
                            Toast.makeText(context, "Item Added", Toast.LENGTH_SHORT).show()
                        },
                        onItemClick = {
                            navController.navigate("${BottomBarDestination.Product.route}/${it.id}")
                        }
                    )
                }
            }
        }
    }
}