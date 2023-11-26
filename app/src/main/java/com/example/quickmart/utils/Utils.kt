package com.example.quickmart.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.example.quickmart.models.User

fun Modifier.noRippleClickable(onClick: () -> Unit) = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

fun Modifier.withClickable(isClickable: Boolean, onClick: () -> Unit): Modifier {
    return if (isClickable) {
        this.noRippleClickable { onClick() }
    } else {
        this
    }
}

var appUser : User? = null