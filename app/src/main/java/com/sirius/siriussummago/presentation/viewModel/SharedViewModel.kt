package com.sirius.siriussummago.presentation.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirius.siriussummago.domain.useCases.AuthUseCase
import com.sirius.siriussummago.presentation.dataModels.AuthState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch

class SharedViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    private val _errors = MutableSharedFlow<Error>()
    val errors: SharedFlow<Error> = _errors

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: MutableStateFlow<AuthState> = _authState

    init {
        viewModelScope.launch {
            _errors.emitAll(authUseCase.errors)
        }
        viewModelScope.launch {
            _authState.emitAll(authUseCase.authState)
        }
        viewModelScope.launch {
            authUseCase.updateAuthStateFromLocal()
        }
    }


    fun saveToken(token: String) {
        Log.d("SharedViewModel", "saveToken: $token")
        viewModelScope.launch {
            authUseCase.execute(token)
        }
    }

    fun signUp(name: String, organization: String, role: String) {
        viewModelScope.launch {
            authUseCase.signUp(name, organization, role)
        }
    }


    // SignUp 2 states
    val signUpName: MutableState<String> = mutableStateOf("")
    val signUpOrganization: MutableState<String> = mutableStateOf("")
    val signUpRole: MutableState<String> = mutableStateOf("")
}
