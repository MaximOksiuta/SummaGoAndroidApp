package com.sirius.siriussummago.presentation.dataModels

data class BaseSummaryInfo(
    val id: String,
    val name: String,
    val disciplineName: String,
    val themeName: String,
    val type: ThemeType,
    val createTime: Long,
    val updateTime: Long
)

