package com.sirius.siriussummago.screens.auth

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.ai.client.generativeai.type.content
import com.sirius.siriussummago.R
import com.sirius.siriussummago.ui.theme.LocalDim
import com.sirius.siriussummago.ui.theme.SummaGoTheme
import com.sirius.siriussummago.ui.theme.shapes

@Composable
fun YandexAuthScreen(authRequest: () -> Unit) {
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

        Column(modifier = Modifier.width(IntrinsicSize.Min)) {
            OutlinedButton(
                onClick = authRequest,
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(horizontal = 15.dp, vertical = 20.dp),
                border = BorderStroke(width = 1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Image(
                        painterResource(R.drawable.yandex_logo),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                    Text(
                        text = "Войти с яндекс",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.width(IntrinsicSize.Max)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Нам необходима авторизация через аккаунт Яндекс, для получения доступа к файлам на вашем диске",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
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
fun YandexAuthScreenPreview() {
    SummaGoTheme {
        YandexAuthScreen {}
    }
}