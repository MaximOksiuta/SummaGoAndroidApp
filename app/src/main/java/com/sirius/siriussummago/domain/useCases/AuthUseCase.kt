package com.sirius.siriussummago.domain.useCases

import android.util.Log
import com.sirius.siriussummago.domain.ErrorProtectedUseCase
import com.sirius.siriussummago.domain.interfaces.LocalRepository
import com.sirius.siriussummago.domain.interfaces.NetworkRepository
import com.sirius.siriussummago.presentation.dataModels.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthUseCase(
    private val networkRepository: NetworkRepository,
    private val localRepository: LocalRepository
) : ErrorProtectedUseCase() {
    suspend fun updateState(token: String) {
        try {
            when (networkRepository.getUserAuthState(token)) {
                true -> {
                    Log.d("AuthUseCase", "auth success; old user")
                    localRepository.saveToken(token)
                    _authState.emit(AuthState.Authenticated)
                }

                false -> {
                    Log.d("AuthUseCase", "auth success; new user")
                    localRepository.saveToken(token)
                    _authState.emit(AuthState.NewUser)
                }
            }
        } catch (e: Exception) {
            Log.d("AuthUseCase", "auth failed - ${e.message}")
            e.printStackTrace()
            _errors.emit(Error("Authentication failed. Please try again"))
            _authState.emit(AuthState.Unauthenticated)
        }
    }

    suspend fun signUp(name: String, organization: String, role: String) {
        if (networkRepository.signUp(localRepository.token, name, organization, role)) {
            _authState.emit(AuthState.Authenticated)
        }
    }

    suspend fun updateAuthStateFromLocal() {
        Log.d("AuthUseCase", "updateAuthStateFromLocal called")
        if (localRepository.token == "") {
            Log.d("AuthUseCase", "token not found in SharedPreferences; authState: Unauthenticated")
            _authState.emit(AuthState.Unauthenticated)
        } else {
            Log.d("AuthUseCase", "token found in SharedPreferences;")
            var receivedAuthState: Boolean? = null
            try {
                receivedAuthState = networkRepository.getUserAuthState(localRepository.token)
            } catch (e: Exception) {
                Log.d("AuthUseCase", "Token check failed. Removing from SharedPreferences")
                localRepository.removeToken()
                e.printStackTrace()
            }

            when (receivedAuthState) {
                true -> {
                    Log.d("AuthUseCase", "auth success; old user")
                    _authState.emit(AuthState.Authenticated)
                }

                false -> {
                    Log.d("AuthUseCase", "auth success; new user")
                    _authState.emit(AuthState.NewUser)
                }

                null -> {
                    Log.d("AuthUseCase", "auth failed")
                    _errors.emit(Error("Authentication failed. Please try again."))
                    _authState.emit(AuthState.Unauthenticated)
                }
            }
        }
    }

    private val _authState = MutableStateFlow<AuthState>(AuthState.NotInitialized)
    val authState: StateFlow<AuthState> = _authState
}