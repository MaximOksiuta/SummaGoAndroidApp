package com.sirius.siriussummago.presentation.screens.auth

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sirius.siriussummago.R
import com.sirius.siriussummago.presentation.MainActivityScreens
import com.sirius.siriussummago.presentation.noRippleClickable
import com.sirius.siriussummago.presentation.ui.NextButton
import com.sirius.siriussummago.presentation.ui.theme.LocalDim
import com.sirius.siriussummago.presentation.ui.theme.SummaGoTheme
import com.sirius.siriussummago.presentation.viewModel.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp2Screen(
    sharedViewModel: SharedViewModel?
) {
    val name = sharedViewModel?.signUpName ?: remember { mutableStateOf("") }
    val organization = sharedViewModel?.signUpOrganization ?: remember { mutableStateOf("") }
    val role = sharedViewModel?.signUpRole ?: remember { mutableStateOf("") }
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

        // Sign up label
        Text(
            text = stringResource(R.string.sign_up_label),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceLarge))

        // Email field
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painterResource(R.drawable.ic_person),
                    contentDescription = "Name icon",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.name_hint),
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
            value = organization.value,
            onValueChange = { organization.value = it },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painterResource(R.drawable.ic_work_place),
                    contentDescription = "Place icon",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.work_place_hint),
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

        Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceMedium))

        OutlinedTextField(
            value = role.value,
            onValueChange = {
                role.value = it
            },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painterResource(R.drawable.ic_role),
                    contentDescription = "Role icon",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.role_hint),
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

        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            NextButton(
                modifier = Modifier.offset(x = LocalDim.current.spaceLarge),
                onClick = {
                    sharedViewModel?.signUp(name = name.value, organization = organization.value, role = role.value)
                }
            )
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
private fun SignUp2ScreenPreview() {
    SummaGoTheme {
        SignUp2Screen(null)
    }
}