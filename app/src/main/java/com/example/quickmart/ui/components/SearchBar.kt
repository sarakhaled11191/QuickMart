package com.example.quickmart.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickmart.ui.theme.H1Color

@Composable
fun SearchBarWithClearIcon(
    modifier: Modifier,
    onTextChange: (String) -> Unit
) {
    val textFieldState = remember { mutableStateOf(TextFieldValue()) }


        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = H1Color,
                )
                Spacer(modifier = Modifier.width(5.dp))
                BasicTextField(
                    value = textFieldState.value,
                    onValueChange = {
                        textFieldState.value = it
                        onTextChange(it.text)
                    },
                    textStyle = TextStyle(fontSize = 20.sp)
                )
            }

            if (textFieldState.value.text.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = null,
                    tint = H1Color,
                    modifier = Modifier
                        .clickable {
                            textFieldState.value = TextFieldValue()
                            onTextChange("")
                        }
                )
            }
        }

}
