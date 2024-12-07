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
    suspend fun execute(token: String) {
        when (networkRepository.getUserAuthState(token)) {
            null -> {
                Log.d("AuthUseCase", "auth failed")
                _errors.emit(Error("Authentication failed. Please try again"))
                _authState.emit(AuthState.Unauthenticated)
            }

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
    }

    suspend fun signUp(name: String, organization: String, role: String) {
        if (networkRepository.signUp(localRepository.token, name, organization, role)) {
            _authState.emit(AuthState.Authenticated)
        }
    }

    suspend fun updateAuthStateFromLocal() {
        if (localRepository.token == "") {
            _authState.emit(AuthState.Unauthenticated)
        } else {
            when (networkRepository.getUserAuthState(localRepository.token)) {
                null -> {
                    Log.d("AuthUseCase", "auth failed")
                    _errors.emit(Error("Authentication failed. Please try again"))
                    _authState.emit(AuthState.Unauthenticated)
                }

                true -> {
                    Log.d("AuthUseCase", "auth success; old user")
                    _authState.emit(AuthState.Authenticated)
                }

                false -> {
                    Log.d("AuthUseCase", "auth success; new user")
                    _authState.emit(AuthState.NewUser)
                }
            }
        }
    }

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState
}