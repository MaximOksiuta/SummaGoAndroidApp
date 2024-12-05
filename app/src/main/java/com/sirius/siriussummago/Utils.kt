package com.sirius.siriussummago

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import java.text.SimpleDateFormat
import java.util.Date

fun Modifier.noRippleClickable(onClick: () -> Unit) = composed {
    Modifier.clickable(remember { MutableInteractionSource() }, null) {
        onClick.invoke()
    }
}

fun getDateTime(s: Long): String? {
    try {
        val sdf = SimpleDateFormat("dd.MM.YYYY")
        val netDate = Date(s * 1000)
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}