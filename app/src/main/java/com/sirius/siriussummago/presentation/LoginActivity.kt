package com.sirius.siriussummago.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sirius.siriussummago.presentation.dataModels.AuthState
import com.sirius.siriussummago.presentation.screens.auth.SignUp2Screen
import com.sirius.siriussummago.presentation.screens.auth.YandexAuthScreen
import com.sirius.siriussummago.presentation.ui.theme.SummaGoTheme
import com.sirius.siriussummago.presentation.viewModel.SharedViewModel
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthSdk
import com.yandex.authsdk.internal.strategy.LoginType
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : ComponentActivity() {
    private val sharedViewModel: SharedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sdk = YandexAuthSdk.create(YandexAuthOptions(this))
        val launcher = registerForActivityResult(sdk.contract) { result -> handleResult(result) }
        val loginOptions = YandexAuthLoginOptions(loginType = LoginType.CHROME_TAB)
        enableEdgeToEdge()
        setContent {
            SummaGoTheme {
                Scaffold { innerPadding ->
                    val navController = rememberNavController()
                    val authState = sharedViewModel.authState.collectAsStateWithLifecycle()
                    when (authState.value) {
                        AuthState.NewUser -> {
                            navController.navigate(LoginActivityDestinations.SignUp.name)
                        }

                        AuthState.Authenticated -> {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }

                        AuthState.Unauthenticated -> {

                        }
                    }
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = LoginActivityDestinations.YandexAuth.name
                    ) {
                        composable(LoginActivityDestinations.YandexAuth.name) {
                            YandexAuthScreen {
                                Log.d("LoginActivity", "calling yandex auth")
                                launcher.launch(loginOptions)
                            }
                        }
                        composable(LoginActivityDestinations.SignUp.name) {
                            SignUp2Screen(sharedViewModel)
                        }
                    }
                }
            }
        }
    }

    fun handleResult(result: YandexAuthResult) {
        when (result) {
            is YandexAuthResult.Success -> {
                Log.d("LoginActivity", "Success - ${result.token}")
                sharedViewModel.saveToken(result.token.value)
            }

            is YandexAuthResult.Failure -> {
                Log.d("LoginActivity", "Failure - ${result.exception}")
            }

            YandexAuthResult.Cancelled -> {
                Log.d("LoginActivity", "Auth Cancelled")
            }
        }
    }
}

enum class LoginActivityDestinations {
    YandexAuth,
    SignUp
}
