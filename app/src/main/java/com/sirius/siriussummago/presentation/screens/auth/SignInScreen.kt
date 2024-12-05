package com.sirius.siriussummago.presentation.screens.auth


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sirius.siriussummago.R
import com.sirius.siriussummago.presentation.MainActivityScreens
import com.sirius.siriussummago.presentation.ui.NextButton
import com.sirius.siriussummago.presentation.ui.theme.LocalDim
import com.sirius.siriussummago.presentation.ui.theme.SummaGoTheme

data class SignInScreenState(
    val email: MutableState<String> = mutableStateOf(""),
    val password: MutableState<String> = mutableStateOf(""),
)

@Composable
fun SignInScreen(
    navController: NavController,
    screenState: SignInScreenState = remember { SignInScreenState() }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = LocalDim.current.spaceLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Logo and app name
        Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceLarge))
        Image(
            painterResource(R.drawable.logo),
            contentDescription = "App logo",
            modifier = Modifier
                .fillMaxWidth(0.37f)
                .clip(MaterialTheme.shapes.large)
        )
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceLarge))

        // Sign in label
        Text(
            text = stringResource(R.string.sign_in_label),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceLarge))

        // Email field
        OutlinedTextField(
            value = screenState.email.value,
            onValueChange = { screenState.email.value = it },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painterResource(R.drawable.ic_mail),
                    contentDescription = "Email icon",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.email_hint),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                focusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                errorBorderColor = MaterialTheme.colorScheme.error
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(Modifier.Companion.height(LocalDim.current.spaceMedium))

        // Password field
        OutlinedTextField(
            value = screenState.password.value,
            onValueChange = { screenState.password.value = it },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painterResource(R.drawable.ic_key),
                    contentDescription = "Email icon",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.password_hint),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                focusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                errorBorderColor = MaterialTheme.colorScheme.error
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceExtraSmall))
        // Create new account button
        Text(
            stringResource(R.string.create_new_account_label),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(MainActivityScreens.SignUp.name)
                }
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            NextButton(modifier = Modifier.offset(x = LocalDim.current.spaceLarge)) {
                navController.navigate(MainActivityScreens.Main.name)
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
)
@Composable
private fun SignInScreenPreview() {
    SummaGoTheme {
        SignInScreen(rememberNavController())
    }
}