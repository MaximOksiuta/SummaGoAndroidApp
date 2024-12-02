package com.sirius.siriussummago

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sirius.siriussummago.screens.auth.SignInScreen
import com.sirius.siriussummago.screens.auth.SignUp2Screen
import com.sirius.siriussummago.screens.auth.SignUpScreen
import com.sirius.siriussummago.ui.theme.SummaGoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            SummaGoTheme {
                NavHost(
                    navController = navController,
                    startDestination = MainActivityScreens.SignIn.name
                ) {
                    composable(MainActivityScreens.SignIn.name) {
                        SignInScreen(navController)
                    }

                    composable(MainActivityScreens.SignUp.name) {
                        SignUpScreen(navController)
                    }

                    composable(MainActivityScreens.SignUp2.name) {
                        SignUp2Screen(navController)
                    }

                    composable(MainActivityScreens.Main.name) {

                    }
                }
            }
        }
    }
}

enum class MainActivityScreens(name: String) {
    SignIn("SignIn"),
    SignUp("SignUp"),
    SignUp2("SignUp2"),
    Main("MainScreen")
}