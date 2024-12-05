package com.sirius.siriussummago.dataModels

data class BaseSummaryInfo(
    val id: Int,
    val name: String,
    val disciplineName: String,
    val themeName: String,
    val type: ThemeType,
    val createTime: Long,
    val updateTime: Long
)

