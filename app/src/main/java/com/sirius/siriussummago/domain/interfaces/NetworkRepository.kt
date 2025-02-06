package com.sirius.siriussummago.domain.interfaces

import com.sirius.siriussummago.presentation.dataModels.BaseSummaryInfo

interface NetworkRepository {
    suspend fun getUserAuthState(token: String): Boolean
    suspend fun signUp(token: String, name: String, organization: String, role: String): Boolean
    suspend fun getSummaries(token: String): List<BaseSummaryInfo>
}