package com.sirius.siriussummago.screens.auth


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sirius.siriussummago.R
import com.sirius.siriussummago.ui.theme.LocalDim
import com.sirius.siriussummago.ui.theme.LocalExColorScheme
import com.sirius.siriussummago.ui.theme.SummaGoTheme

data class SignInScreenState(
    val email: MutableState<String> = mutableStateOf(""),
    val password: MutableState<String> = mutableStateOf(""),
)

@Composable
fun SignInScreen(screenState: SignInScreenState = remember { SignInScreenState() }) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = LocalDim.current.spaceLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Logo and app name
        Spacer(modifier = Modifier.height(LocalDim.current.spaceLarge))
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

        Spacer(modifier = Modifier.height(LocalDim.current.spaceLarge))

        // Sign in label
        Text(
            text = stringResource(R.string.signInLabel),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(LocalDim.current.spaceLarge))

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
                    text = stringResource(R.string.emailHint),
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

        Spacer(Modifier.height(LocalDim.current.spaceMedium))

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
                    text = stringResource(R.string.passwordHint),
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

        Spacer(modifier = Modifier.height(LocalDim.current.spaceExtraSmall))
        // Create new account button
        Text(
            stringResource(R.string.create_new_account_label),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            ConstraintLayout(modifier = Modifier.offset(x = LocalDim.current.spaceLarge)) {
                val (buttonBackgroundRef, buttonArrowRef) = createRefs()
                Icon(
                    painter = painterResource(R.drawable.next_button_background),
                    contentDescription = "Next",
                    tint = LocalExColorScheme.current.accent.color,
                    modifier = Modifier.constrainAs(buttonBackgroundRef) {
                        centerTo(parent)
                    }
                )
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_forward),
                    contentDescription = "Next",
                    tint = LocalExColorScheme.current.accent.onColor,
                    modifier = Modifier.constrainAs(buttonArrowRef) {
                        centerHorizontallyTo(parent)
                        linkTo(parent.top, parent.bottom, bias = 0.73f)
                    }
                )
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
        SignInScreen()
    }
}