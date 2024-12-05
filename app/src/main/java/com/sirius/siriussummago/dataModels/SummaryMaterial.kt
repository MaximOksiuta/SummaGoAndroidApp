package com.sirius.siriussummago.dataModels


sealed class SummaryMaterial(
    val file: String,
    val loadedTime: Long,
    val summaryStatus: MaterialSummaryStatus,
    val useInGeneralSummary: Boolean
) {
    class Text(
        val name: String,
        file: String,
        loadedTime: Long,
        summaryStatus: MaterialSummaryStatus,
        useInGeneralSummary: Boolean
    ) : SummaryMaterial(file, loadedTime, summaryStatus, useInGeneralSummary)

    class Image(
        file: String,
        loadedTime: Long,
        summaryStatus: MaterialSummaryStatus,
        useInGeneralSummary: Boolean
    ) : SummaryMaterial(file, loadedTime, summaryStatus, useInGeneralSummary)

    class Audio(
        val duration: Long,
        file: String,
        loadedTime: Long,
        summaryStatus: MaterialSummaryStatus,
        useInGeneralSummary: Boolean
    ) : SummaryMaterial(file, loadedTime, summaryStatus, useInGeneralSummary)

    class Video(
        val duration: Long,
        file: String,
        loadedTime: Long,
        summaryStatus: MaterialSummaryStatus,
        useInGeneralSummary: Boolean
    ) : SummaryMaterial(file, loadedTime, summaryStatus, useInGeneralSummary)
}
