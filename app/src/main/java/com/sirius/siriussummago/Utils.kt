package com.sirius.siriussummago

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.noRippleClickable(onClick: () -> Unit) = composed {
    Modifier.clickable(remember { MutableInteractionSource() }, null) {
        onClick.invoke()
    }
}