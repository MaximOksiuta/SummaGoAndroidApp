package com.sirius.siriussummago.dataModels

data class FullSummaryInfo(
    val id: Int,
    val name: String,
    val subject: SummarySubject,
    val theme: SummaryTheme,
    val type: ThemeType,
    val createTime: Long,
    val updateTime: Long,
    val summaryReady: Boolean,
    val materials: List<SummaryMaterial>,
)
