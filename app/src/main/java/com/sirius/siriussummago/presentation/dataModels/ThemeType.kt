package com.sirius.siriussummago.presentation.dataModels

import androidx.annotation.StringRes
import com.sirius.siriussummago.R

enum class ThemeType(val nameStringId: Int) {
    Lecture(R.string.lection),
    Seminar(R.string.seminar)
}