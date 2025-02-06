package com.sirius.siriussummago.presentation.screens

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sirius.siriussummago.R
import com.sirius.siriussummago.presentation.dataModels.BaseSummaryInfo
import com.sirius.siriussummago.presentation.dataModels.ThemeType
import com.sirius.siriussummago.presentation.ui.TopBar
import com.sirius.siriussummago.presentation.ui.theme.LocalDim
import com.sirius.siriussummago.presentation.ui.theme.LocalExColorScheme
import com.sirius.siriussummago.presentation.ui.theme.SummaGoTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


interface MainScreenDataProvider {
    fun getLastSummaries(): Flow<List<BaseSummaryInfo>>

    fun getAdvice(): Flow<AnnotatedString>
}

@Composable
fun MainScreen(dataProvider: MainScreenDataProvider, navController: NavController) {
    val lastSummaries = dataProvider.getLastSummaries().collectAsStateWithLifecycle(emptyList())
    val currentAdvice =
        dataProvider.getAdvice().collectAsStateWithLifecycle(buildAnnotatedString { })

    Box { // Main box
        Column( // Main column
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background)
                .padding(horizontal = LocalDim.current.spaceMedium)

        ) {
            Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceLarge))


            TopBar(textRes = R.string.app_name, iconRes = R.drawable.ic_person, onClick = {

            })
            Box { // Scroll shading box
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) { // scrollable column
                    Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceLarge))
                    AnimatedVisibility(
                        lastSummaries.value.isNotEmpty(),
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    LocalExColorScheme.current.accent.color,
                                    RoundedCornerShape(24.dp)
                                )
                                .padding(LocalDim.current.spaceMedium)
                        ) { // last summaries column
                            Text(
                                text = stringResource(R.string.last_summaries_label),
                                style = typography.bodyLarge,
                                color = LocalExColorScheme.current.accent.onColor
                            )
                            Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceMedium))
                            Box { // dividing box
                                Row(modifier = Modifier.fillMaxWidth()) { // summaries info

                                    // names
                                    Column(modifier = Modifier.weight(1f)) {
                                        for (summary_id in lastSummaries.value.indices.take(3)) {
                                            Text(
                                                text = lastSummaries.value.getOrNull(summary_id)
                                                    ?.let { summary -> "${summary.name} (${summary.disciplineName})" }
                                                    ?: "", style = typography.bodySmall,
                                                color = LocalExColorScheme.current.accent.onColor,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.height(16.dp)
                                            )
                                            Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceMedium))
                                        }
                                    }
                                    Spacer(modifier = Modifier.Companion.width(LocalDim.current.spaceExtraSmall))

                                    // types
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.width(IntrinsicSize.Min)
                                    ) {
                                        for (summary_id in lastSummaries.value.indices) {
                                            Text(
                                                text = stringResource((lastSummaries.value[summary_id].type.nameStringId)),
                                                style = typography.bodySmall,
                                                color = LocalExColorScheme.current.accent.onColorContainer,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(
                                                        LocalExColorScheme.current.accent.colorContainer,
                                                        CircleShape
                                                    )
                                                    .height(16.dp)
                                                    .padding(horizontal = 6.dp)
                                            )
                                            Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceMedium))
                                        }
                                    }
                                }
                                Column {
                                    for (summary_id in lastSummaries.value.indices) {
                                        Spacer(modifier = Modifier.height(16.dp))
                                        if (summary_id != (lastSummaries.value.size - 1)) {
                                            Box(
                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier.Companion.height(LocalDim.current.spaceMedium)
                                            ) {
                                                HorizontalDivider(
                                                    thickness = 1.dp,
                                                    color = colorScheme.outline
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    AnimatedVisibility(
                        lastSummaries.value.isEmpty(),
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    LocalExColorScheme.current.accent.color,
                                    RoundedCornerShape(24.dp)
                                )
                                .padding(LocalDim.current.spaceMedium),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) { // last summaries column
                            Text(
                                text = stringResource(R.string.last_summaries_label_when_empty),
                                style = typography.bodyLarge,
                                color = LocalExColorScheme.current.accent.onColor
                            )
                            Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceMedium))
                            Button(colors = ButtonDefaults.buttonColors(
                                containerColor = LocalExColorScheme.current.accent.colorContainer
                            ), onClick = {
                                TODO("Create summary")
                            }) {
                                Text(
                                    text = stringResource(R.string.create_summary_at_last_summaries_label),
                                    color = LocalExColorScheme.current.accent.onColorContainer
                                )
                            }
                        }
                    }
                    // Feature buttons
                    Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceMedium))
                    Row(horizontalArrangement = Arrangement.spacedBy(LocalDim.current.spaceMedium)) {
                        FeatureButton(
                            modifier = Modifier.weight(1f),
                            image = R.drawable.logo,
                            backgroundColor = colorScheme.secondaryContainer,
                            name = stringResource(R.string.summaries_button_main_screen_label)
                        ) {

                        }

                        FeatureButton(
                            modifier = Modifier.weight(1f),
                            image = R.drawable.search_info,
                            backgroundColor = colorScheme.tertiaryContainer,
                            name = stringResource(R.string.search_button_main_screen_label)
                        ) {

                        }
                    }
                    Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceMedium))
                    Row(horizontalArrangement = Arrangement.spacedBy(LocalDim.current.spaceMedium)) {
                        FeatureButton(
                            modifier = Modifier.weight(1f),
                            image = R.drawable.summaries_store,
                            backgroundColor = colorScheme.primaryContainer,
                            name = stringResource(R.string.summary_shop_button_main_screen_label)
                        ) {

                        }

                        FeatureButton(
                            modifier = Modifier.weight(1f),
                            image = R.drawable.self_testing,
                            backgroundColor = colorScheme.secondaryContainer,
                            name = stringResource(R.string.selftest_button_main_screen_label)
                        ) {

                        }
                    }

                    Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceMedium))

                    Card(
                        modifier = Modifier,
                        colors = CardDefaults.cardColors(
                            containerColor = colorScheme.tertiaryContainer
                        ),
                        shape = RoundedCornerShape(24.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp
                        )
                    ) {
                        Column(modifier = Modifier.padding(15.dp)) {
                            Text(
                                text = stringResource(R.string.council_label),
                                style = typography.titleMedium,
                                color = colorScheme.onSecondaryContainer
                            )
                            Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceExtraSmall))
                            Text(
                                text = currentAdvice.value,
                                style = typography.bodyMedium,
                                color = colorScheme.onSecondaryContainer,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(100.dp))
                }


                // Shader
                Box(
                    modifier = Modifier.Companion
                        .height(LocalDim.current.spaceLarge)
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    colorScheme.background,
                                    Color.Transparent
                                )
                            )
                        )
                )
            }
        }

        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(LocalDim.current.spaceLarge),
            containerColor = colorScheme.onPrimaryContainer
        ) {
            Icon(
                painterResource(R.drawable.ic_plus),
                contentDescription = "add new summary",
                tint = colorScheme.primaryContainer,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun FeatureButton(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    backgroundColor: Color,
    name: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        onClick = onClick
    ) {
        Image(
            painterResource(image),
            contentDescription = name,
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(24.dp))

        )
        Text(
            text = name,
            style = typography.bodySmall,
            color = colorScheme.onSecondaryContainer,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 5.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Composable
private fun MainScreenPreview() {
    SummaGoTheme {
        val navController = rememberNavController()
        MainScreen(
            navController = navController,
            dataProvider = object : MainScreenDataProvider {
                override fun getLastSummaries(): Flow<List<BaseSummaryInfo>> = flow {
                    emit(
                        listOf<BaseSummaryInfo>(
                            BaseSummaryInfo(
                                id = "0",
                                name = "Теория множеств",
                                disciplineName = "Мат. анализ",
                                themeName = "Theme 1",
                                type = ThemeType.Lecture,
                                createTime = 1728248400,
                                updateTime = 1728248400
                            ),
                            BaseSummaryInfo(
                                id = "0",
                                name = "Расстояние Лихтенштейна",
                                disciplineName = "Алг. и структуры данных",
                                themeName = "Theme 1",
                                type = ThemeType.Seminar,
                                createTime = 1728248400,
                                updateTime = 1728248400
                            ),
                            BaseSummaryInfo(
                                id = "0",
                                name = "Октябрьская революция",
                                disciplineName = "История",
                                themeName = "Theme 1",
                                type = ThemeType.Lecture,
                                createTime = 1728248400,
                                updateTime = 1728248400
                            )
                        )
                    )
                }

                override fun getAdvice(): Flow<AnnotatedString> = flow {
                    emit(buildAnnotatedString {
                        pushStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold))
                        append("Работайте с конспектами")

                        pushStyle(style = SpanStyle(fontWeight = FontWeight.Normal))
                        append(" — регулярно пересматривайте записи и материалы лекций, это облегчит подготовку к экзаменам.")
                    })
                }

            })
    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun FeatureButtonPreview() {
    SummaGoTheme {
        Row(
            modifier = Modifier
                .fillMaxSize(1f)
                .background(colorScheme.background)
                .padding(100.dp)
        ) {
            FeatureButton(
                modifier = Modifier.weight(1f),
                image = R.drawable.logo,
                backgroundColor = colorScheme.secondaryContainer,
                name = "Создание конспектов"
            ) {

            }
        }
    }
}
