package com.example.quickmart.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickmart.models.FavouriteItem
import com.example.quickmart.ui.theme.H1Color

@Composable
fun FavouriteItemUi(
    favouriteItem: FavouriteItem,
    onDeleteIconClicked: (FavouriteItem) -> Unit,
) {
    val resources = LocalContext.current.resources
    val packageName = LocalContext.current.packageName
    val imageResId = resources.getIdentifier(favouriteItem.productImage, "drawable", packageName)
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
                Image(
                    painter = painterResource(id = imageResId),
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
                                text = favouriteItem.productName,
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
                                onDeleteIconClicked(favouriteItem)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    Text(
                        text = "$${String.format("%.2f", favouriteItem.unitPrice)}",
                        color = H1Color,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = Color.Gray.copy(0.19f))
        }
    }
}