package com.example.quickmart.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.quickmart.models.Product
import com.example.quickmart.ui.theme.GreenColor
import com.example.quickmart.ui.theme.H1Color

@Composable
fun ProductItem(
    product: Product,
    onAddClick: (Product) -> Unit,
    onItemClick: (Product) -> Unit,
) {
    val resources = LocalContext.current.resources
    val packageName = LocalContext.current.packageName
    val imageResId = resources.getIdentifier(product.imageName, "drawable", packageName)

    ConstraintLayout(
        modifier = Modifier
            .size(340.dp)
            .border(1.dp, Color.Gray.copy(0.2f), shape = RoundedCornerShape(15.dp))
            .padding(15.dp)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) {
                onItemClick(product)
            }
    ) {
        val (productImage, productName, productDisc, productPrice, addButton) = createRefs()

        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "productImage",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .constrainAs(productImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(130.dp)
        )

        Text(
            text = product.title,
            color = H1Color,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(productName) {
                    top.linkTo(productImage.bottom)
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints
                }
                .padding(top = 5.dp)
        )
        Text(
            text = product.description,
            color = Color.Gray.copy(0.4f),
            fontSize = 17.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(productDisc) {
                    top.linkTo(productName.bottom)
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints
                }
                .padding(top = 5.dp)
        )

        Text(
            text = "$${product.price}",
            color = H1Color,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(productPrice) {
                    start.linkTo(parent.start)
                    top.linkTo(addButton.top)
                    bottom.linkTo(addButton.bottom)
                }
        )

        Box(
            modifier = Modifier
                .background(
                    color = GreenColor,
                    shape = RoundedCornerShape(15.dp)
                )
                .clickable {
                    onAddClick(product)
                }
                .constrainAs(addButton) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(vertical = 11.dp, horizontal = 17.dp)
        ) {
            Text(
                text = "+",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}
