package com.sirius.siriussummago.domain.useCases

import android.util.Log
import com.sirius.siriussummago.domain.ErrorProtectedUseCase
import com.sirius.siriussummago.domain.interfaces.LocalRepository
import com.sirius.siriussummago.domain.interfaces.NetworkRepository
import com.sirius.siriussummago.presentation.dataModels.BaseSummaryInfo

class GetSummariesUseCase(
    private val networkRepository: NetworkRepository,
    private val localRepository: LocalRepository
) :
    ErrorProtectedUseCase() {
    suspend fun getSummaries(): List<BaseSummaryInfo> {
        try {
            return networkRepository.getSummaries(localRepository.token)
        } catch (e: Exception) {
            Log.d("AuthUseCase", "auth failed - ${e.message}")
            _errors.emit(Error("Authentication failed. Please try again"))
            return emptyList()
        }
    }
}