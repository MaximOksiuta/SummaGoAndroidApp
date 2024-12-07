package com.sirius.siriussummago.data.datamodels

import kotlinx.serialization.Serializable


@Serializable
data class CheckTokenResponseModel(val id: Int, val exist: Boolean)
