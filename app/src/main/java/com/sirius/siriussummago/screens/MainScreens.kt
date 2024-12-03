package com.sirius.siriussummago.screens

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
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
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sirius.siriussummago.R
import com.sirius.siriussummago.dataModels.BaseSummaryInfo
import com.sirius.siriussummago.dataModels.ThemeType
import com.sirius.siriussummago.ui.theme.LocalDim
import com.sirius.siriussummago.ui.theme.LocalExColorScheme
import com.sirius.siriussummago.ui.theme.SummaGoTheme
import com.sirius.siriussummago.ui.theme.backgroundDark
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread


interface MainScreenDataProvider {
    fun getLastSummaries(): Flow<List<BaseSummaryInfo>>

    fun getAdvice(): Flow<AnnotatedString>
}

@Composable
fun MainScreen(dataProvider: MainScreenDataProvider) {
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
            Spacer(modifier = Modifier.height(LocalDim.current.spaceLarge))


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) { // Top bar with app name and profile button
                Spacer(modifier = Modifier.weight(1f))

                // App name
                Text(
                    text = stringResource(R.string.app_name),
                    style = typography.titleLarge,
                    color = colorScheme.onBackground
                )
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Profile button
                    Icon(
                        painterResource(R.drawable.ic_person),
                        contentDescription = "User profile button",
                        tint = colorScheme.onBackground,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(modifier = Modifier.width(LocalDim.current.spaceMedium))
                }
            }
            val scrollState = rememberScrollState()
            thread {
                runBlocking {
                    scrollState.scrollBy(0.5f)
                }
            }
            Box { // Scroll shading box
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) { // scrollable column
                    Spacer(modifier = Modifier.height(LocalDim.current.spaceExtraLarge))
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
                            text = "Последние конспекты",
                            style = typography.bodyLarge,
                            color = LocalExColorScheme.current.accent.onColor
                        )
                        Spacer(modifier = Modifier.height(LocalDim.current.spaceMedium))
                        Box { // dividing box
                            Row(modifier = Modifier.fillMaxWidth()) { // summaries info

                                // names
                                Column(modifier = Modifier.weight(1f)) {
                                    for (summary_id in lastSummaries.value.indices) {
                                        Text(
                                            text = lastSummaries.value.getOrNull(summary_id)
                                                ?.let { summary -> "${summary.name} (${summary.disciplineName})" }
                                                ?: "", style = typography.bodySmall,
                                            color = LocalExColorScheme.current.accent.onColor,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.height(16.dp)
                                        )
                                        Spacer(modifier = Modifier.height(LocalDim.current.spaceMedium))
                                    }
                                }
                                Spacer(modifier = Modifier.width(LocalDim.current.spaceExtraSmall))

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
                                                .padding(horizontal = 4.dp)
                                        )
                                        Spacer(modifier = Modifier.height(LocalDim.current.spaceMedium))
                                    }
                                }
                            }
                            Column {
                                for (summary_id in lastSummaries.value.indices) {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    if (summary_id != (lastSummaries.value.size - 1)) {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier.height(LocalDim.current.spaceMedium)
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
                    // Feature buttons
                    Spacer(modifier = Modifier.height(LocalDim.current.spaceMedium))
                    Row(horizontalArrangement = Arrangement.spacedBy(LocalDim.current.spaceMedium)) {
                        FeatureButton(
                            modifier = Modifier.weight(1f),
                            image = R.drawable.logo,
                            backgroundColor = colorScheme.secondaryContainer,
                            name = "Создание конспектов"
                        ) {

                        }

                        FeatureButton(
                            modifier = Modifier.weight(1f),
                            image = R.drawable.search_info,
                            backgroundColor = colorScheme.tertiaryContainer,
                            name = "Поиск информации"
                        ) {

                        }
                    }
                    Spacer(modifier = Modifier.height(LocalDim.current.spaceMedium))
                    Row(horizontalArrangement = Arrangement.spacedBy(LocalDim.current.spaceMedium)) {
                        FeatureButton(
                            modifier = Modifier.weight(1f),
                            image = R.drawable.summaries_store,
                            backgroundColor = colorScheme.primaryContainer,
                            name = "Магазин конспектов"
                        ) {

                        }

                        FeatureButton(
                            modifier = Modifier.weight(1f),
                            image = R.drawable.self_testing,
                            backgroundColor = colorScheme.secondaryContainer,
                            name = "Самотестирование"
                        ) {

                        }
                    }

                    Spacer(modifier = Modifier.height(LocalDim.current.spaceMedium))

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
                                text = "Полезные советы",
                                style = typography.titleMedium,
                                color = colorScheme.onSecondaryContainer
                            )
                            Spacer(modifier = Modifier.height(LocalDim.current.spaceExtraSmall))
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
                    modifier = Modifier
                        .height(LocalDim.current.spaceExtraLarge)
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
        MainScreen(dataProvider = object : MainScreenDataProvider {
            override fun getLastSummaries(): Flow<List<BaseSummaryInfo>> {
                return flow {
                    emit(
                        listOf<BaseSummaryInfo>(
                            BaseSummaryInfo(
                                id = 0,
                                name = "Теория множеств",
                                disciplineName = "Мат. анализ",
                                type = ThemeType.Lecture
                            ),
                            BaseSummaryInfo(
                                id = 0,
                                name = "Расстояние Лихтенштейна",
                                disciplineName = "Алг. и структуры данных",
                                type = ThemeType.Seminar
                            ),
                            BaseSummaryInfo(
                                id = 0,
                                name = "Октябрьская революция",
                                disciplineName = "История",
                                type = ThemeType.Lecture
                            )
                        )
                    )
                }
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
