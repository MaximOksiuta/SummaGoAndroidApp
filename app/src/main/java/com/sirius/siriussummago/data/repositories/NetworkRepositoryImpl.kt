package com.sirius.siriussummago.data.repositories

import android.util.Log
import com.sirius.siriussummago.data.api.SummaGoService
import com.sirius.siriussummago.domain.interfaces.NetworkRepository
import com.sirius.siriussummago.presentation.dataModels.BaseSummaryInfo

class NetworkRepositoryImpl : NetworkRepository {
    override suspend fun getUserAuthState(token: String): Boolean {
        return return SummaGoService.checkToken(token)
    }

    override suspend fun signUp(
        token: String,
        name: String,
        organization: String,
        role: String
    ): Boolean {
        try {
            SummaGoService.signUp(token, name, organization, role)
            return true
        } catch (e: Exception){
            Log.e("NetworkRepository", e.toString())
            return false
        }
    }

    override suspend fun getSummaries(token: String): List<BaseSummaryInfo> {
        return emptyList()
    }
}