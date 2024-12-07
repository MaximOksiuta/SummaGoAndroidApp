package com.sirius.siriussummago.data.datamodels

import kotlinx.serialization.Serializable


@Serializable
data class SignUpRequestModel(
    val job: String,
    val name: String,
    val workplace: String
)