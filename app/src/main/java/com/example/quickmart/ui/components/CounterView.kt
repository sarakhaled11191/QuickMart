package com.example.quickmart.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickmart.models.CartItem
import com.example.quickmart.ui.theme.GreenColor
import com.example.quickmart.ui.theme.H1Color
import com.example.quickmart.utils.noRippleClickable
import com.example.quickmart.utils.withClickable

@Composable
fun CounterView(
    modifier: Modifier,
    quantityP: Int,
    onPlusButtonClicked: () -> Unit,
    onMiensButtonClicked: () -> Unit,
) {
    var quantity by remember { mutableIntStateOf(quantityP) }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Box(
            modifier = Modifier
                .border(1.dp, Color.Gray.copy(0.4f), shape = RoundedCornerShape(15.dp))
                .clip(RoundedCornerShape(15.dp))
                .background(
                    Color.White,
                    RoundedCornerShape(15.dp)
                )
                .padding(vertical = 5.dp, horizontal = 11.dp)
                .withClickable(quantity > 1) {
                    quantity -= 1
                    onMiensButtonClicked()
                }
        ) {
            Text(
                text = "-",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = if (quantity > 1) GreenColor else Color.Gray
            )
        }
        Text(
            text = quantity.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = H1Color
        )
        Box(
            modifier = Modifier
                .border(1.dp, Color.Gray.copy(0.4f), shape = RoundedCornerShape(15.dp))
                .background(
                    Color.White,
                    RoundedCornerShape(15.dp)
                )
                .padding(vertical = 5.dp, horizontal = 11.dp)
                .withClickable(true) {
                    quantity += 1
                    onPlusButtonClicked()
                }
        ) {
            Text(
                text = "+",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = GreenColor
            )
        }
    }
}

