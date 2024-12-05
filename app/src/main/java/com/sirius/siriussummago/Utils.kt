package com.sirius.siriussummago

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import okhttp3.internal.UTC
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
fun getTime(s: Long): String? {

    val hours = s / 3600
    val minutes = (s % 3600) / 60
    val seconds = s % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}