package com.sirius.siriussummago.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sirius.siriussummago.presentation.ui.theme.SummaGoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            SummaGoTheme {
                NavHost(
                    navController = navController,
                    startDestination = MainActivityScreens.Main.name
                ) {
                    composable(MainActivityScreens.Main.name) {

                    }
                }
            }
        }
    }
}

enum class MainActivityScreens {
    Main
}