package com.sirius.siriussummago.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sirius.siriussummago.R
import com.sirius.siriussummago.presentation.dataModels.AuthState
import com.sirius.siriussummago.presentation.ui.theme.LocalDim
import com.sirius.siriussummago.presentation.ui.theme.SummaGoTheme
import com.sirius.siriussummago.presentation.viewModel.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    private val sharedViewModel: SharedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val authState = sharedViewModel.authState.collectAsStateWithLifecycle()

            Log.d("SplashActivity", authState.value.name)
            if (authState.value == AuthState.Authenticated) {
                Log.d("SplashActivity", "navigate to main activity")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else if (authState.value != AuthState.NotInitialized) {
                Log.d("SplashActivity", "navigate to login activity")
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            SummaGoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .clip(CircleShape),
                            painter = painterResource(R.drawable.logo),
                            contentDescription = "logo"
                        )
                        Spacer(modifier = Modifier.height(LocalDim.current.spaceMedium))
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SummaGoTheme {
        Greeting("Android")
    }
}