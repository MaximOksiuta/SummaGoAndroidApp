package com.sirius.siriussummago.presentation.screens

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sirius.siriussummago.R
import com.sirius.siriussummago.presentation.dataModels.BaseSummaryInfo
import com.sirius.siriussummago.presentation.dataModels.SummarySubject
import com.sirius.siriussummago.presentation.dataModels.SummaryTheme
import com.sirius.siriussummago.presentation.dataModels.ThemeType
import com.sirius.siriussummago.presentation.getDateTime
import com.sirius.siriussummago.presentation.noRippleClickable
import com.sirius.siriussummago.presentation.ui.TopBar
import com.sirius.siriussummago.presentation.ui.theme.LocalDim
import com.sirius.siriussummago.presentation.ui.theme.LocalExColorScheme
import com.sirius.siriussummago.presentation.ui.theme.SummaGoTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface SummariesListScreenDataProvider {
    fun getSubjects(): Flow<List<SummarySubject>>

    fun getSummaries(): Flow<List<BaseSummaryInfo>>
}

@Composable
fun SummariesListScreen(dataProvider: SummariesListScreenDataProvider) {

    val searchState = rememberTextFieldState()
    val subjects = dataProvider.getSubjects().collectAsStateWithLifecycle(emptyList())

    val themes = remember {
        mutableStateOf(
            listOf(
                SummaryTheme(id = 0, name = "Theme 1"),
                SummaryTheme(id = 1, name = "Theme 2"),
                SummaryTheme(id = 2, name = "Theme 3")
            )
        )
    }

    val selectedSubjectId = remember { mutableIntStateOf(-1) }
    val selectedThemeId = remember { mutableIntStateOf(-1) }
    val selectedType = remember { mutableStateOf<ThemeType?>(null) }

    val summaries = dataProvider.getSummaries().collectAsStateWithLifecycle(emptyList())


    Box { // Main box
        Column( // Main column
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background)
                .padding(start = LocalDim.current.spaceMedium)

        ) {
            Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceLarge))

            TopBar(
                modifier = Modifier.padding(horizontal = LocalDim.current.spaceMedium),
                textRes = R.string.my_summaries_label
            )

            Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceLarge))


            BasicTextField(
                state = searchState,
                modifier = Modifier
                    .padding(end = LocalDim.current.spaceMedium)
                    .height(40.dp)
                    .fillMaxWidth()
                    .background(LocalExColorScheme.current.accent.color, shape = CircleShape),
                textStyle = typography.bodyMedium.copy(color = LocalExColorScheme.current.accent.onColor),
                decorator = {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = LocalDim.current.spaceMedium),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box {
                            if (searchState.text.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.search_summary_hint),
                                    style = typography.bodyMedium,
                                    color = LocalExColorScheme.current.accent.onColor
                                )
                            }
                            it()
                        }

                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painterResource(R.drawable.ic_search),
                            contentDescription = "search",
                            tint = LocalExColorScheme.current.accent.onColor
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceMedium))

            Text(
                text = stringResource(R.string.filters_label),
                style = typography.titleMedium,
                color = colorScheme.onBackground
            )

            Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceSmall))

            // Subjects filter row
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(16.dp)) {
                Text(
                    text = stringResource(R.string.subjects_filter_label) + ':',
                    style = typography.titleSmall,
                    color = colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    lineHeight = 0.5.sp
                )
                Box {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        item {
                            Spacer(modifier = Modifier.Companion.width(LocalDim.current.spaceSmall))
                        }
                        if (selectedSubjectId.intValue == -1) {
                            itemsIndexed(subjects.value) { index, it ->
                                TextBubble(
                                    text = it.name,
                                    bubbleColor = colorScheme.tertiary,
                                    textColor = colorScheme.onTertiary
                                ) {
                                    selectedSubjectId.intValue = it.id
                                }
                            }
                        } else {
                            item {
                                TextBubble(
                                    text = subjects.value.find { it.id == selectedSubjectId.intValue }?.name
                                        ?: "",
                                    bubbleColor = colorScheme.tertiary,
                                    textColor = colorScheme.onTertiary
                                ) { }
                            }
                            item {
                                Icon(
                                    painter = painterResource(R.drawable.ic_plus),
                                    contentDescription = null,
                                    tint = colorScheme.onBackground,
                                    modifier = Modifier
                                        .rotate(45f)
                                        .size(16.dp)
                                        .noRippleClickable {
                                            selectedSubjectId.intValue = -1
                                        }
                                )
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.Companion.width(LocalDim.current.spaceMedium))
                        }
                    }

                    Box(
                        modifier = Modifier.Companion
                            .width(LocalDim.current.spaceSmall)
                            .fillMaxHeight()
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        colorScheme.background,
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceSmall))


            // Themes filter row
            val themesFilterHeight =
                animateDpAsState(
                    if (selectedSubjectId.intValue == -1) 0.dp else 16.dp + LocalDim.current.spaceSmall,
                    label = ""
                )

            Column(modifier = Modifier.height(themesFilterHeight.value)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.themes_filter_label) + ':',
                        style = typography.titleSmall,
                        color = colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        lineHeight = 0.5.sp
                    )
                    Box {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            item {
                                Spacer(modifier = Modifier.Companion.width(LocalDim.current.spaceSmall))
                            }
                            if (selectedThemeId.intValue == -1) {
                                itemsIndexed(themes.value) { index, it ->
                                    TextBubble(
                                        text = it.name,
                                        bubbleColor = colorScheme.tertiary,
                                        textColor = colorScheme.onTertiary
                                    ) {
                                        selectedThemeId.intValue = it.id
                                    }
                                }
                            } else {
                                item {
                                    TextBubble(
                                        text = themes.value.find { it.id == selectedThemeId.intValue }?.name
                                            ?: "",
                                        bubbleColor = colorScheme.tertiary,
                                        textColor = colorScheme.onTertiary
                                    ) { }
                                }
                                item {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_plus),
                                        contentDescription = null,
                                        tint = colorScheme.onBackground,
                                        modifier = Modifier
                                            .rotate(45f)
                                            .size(16.dp)
                                            .noRippleClickable {
                                                selectedThemeId.intValue = -1
                                                selectedThemeId.intValue = -1
                                            }
                                    )
                                }
                            }
                            item {
                                Spacer(modifier = Modifier.Companion.width(LocalDim.current.spaceMedium))
                            }
                        }

                        Box(
                            modifier = Modifier.Companion
                                .width(LocalDim.current.spaceSmall)
                                .fillMaxHeight()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        listOf(
                                            colorScheme.background,
                                            Color.Transparent
                                        )
                                    )
                                )
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }


            // Types filter row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.type_label) + ':',
                    style = typography.titleSmall,
                    color = colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    lineHeight = 0.5.sp
                )
                Box {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        item {
                            Spacer(modifier = Modifier.Companion.width(LocalDim.current.spaceSmall))
                        }
                        if (selectedType.value == null) {
                            items(
                                listOf(
                                    ThemeType.Lecture,
                                    ThemeType.Seminar
                                )
                            ) {
                                TextBubble(
                                    text = stringResource(it.nameStringId),
                                    bubbleColor = colorScheme.onSecondary,
                                    textColor = colorScheme.secondary
                                ) {
                                    selectedType.value = it
                                }
                            }
                        } else {
                            item {
                                TextBubble(
                                    text = stringResource(selectedType.value!!.nameStringId),
                                    bubbleColor = colorScheme.onSecondary,
                                    textColor = colorScheme.secondary
                                ) { }
                            }
                            item {
                                Icon(
                                    painter = painterResource(R.drawable.ic_plus),
                                    contentDescription = null,
                                    tint = colorScheme.onBackground,
                                    modifier = Modifier
                                        .rotate(45f)
                                        .size(16.dp)
                                        .noRippleClickable {
                                            selectedType.value = null
                                        }
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier.Companion
                            .width(LocalDim.current.spaceSmall)
                            .fillMaxHeight()
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        colorScheme.background,
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                }
            }

            Box { // Scroll shading box
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = LocalDim.current.spaceMedium)
                ) { // scrollable column
                    item {
                        Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceMedium))
                    }
                    items(summaries.value) {
                        SummaryInfoCard(modifier = Modifier.fillMaxWidth(), baseSummaryInfo = it)
                        Spacer(modifier = Modifier.Companion.height(LocalDim.current.spaceMedium))
                    }
                }

                // Shader
                Box(
                    modifier = Modifier.Companion
                        .height(LocalDim.current.spaceMedium)
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
    }
}

@Composable
fun TextBubble(
    modifier: Modifier = Modifier,
    text: String,
    bubbleColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                bubbleColor,
                CircleShape
            )
            .noRippleClickable {
                onClick.invoke()
            }
            .padding(horizontal = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = typography.bodySmall,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun SummaryInfoCard(modifier: Modifier = Modifier, baseSummaryInfo: BaseSummaryInfo) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceVariant
        ),
        modifier = modifier.height(IntrinsicSize.Min)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalDim.current.spaceMedium)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = baseSummaryInfo.name,
                    style = typography.titleSmall,
                    color = colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.Companion.width(LocalDim.current.spaceLarge))
                Text(
                    text = stringResource(R.string.subject_label) + ": " + baseSummaryInfo.disciplineName,
                    style = typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.Companion.width(LocalDim.current.spaceSmall))
                Text(
                    text = stringResource(R.string.theme_label) + ": " + baseSummaryInfo.themeName,
                    style = typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.Companion.width(LocalDim.current.spaceSmall))
                Text(
                    text = stringResource(R.string.date_label) + ": " + getDateTime(baseSummaryInfo.createTime),
                    style = typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(modifier = Modifier.Companion.width(LocalDim.current.spaceSmall))

            TextBubble(
                modifier = Modifier.height(20.dp),
                text = stringResource(baseSummaryInfo.type.nameStringId),
                bubbleColor = colorScheme.secondary,
                textColor = colorScheme.onSecondary
            ) { }
        }
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
fun SummaryInfoCardPreview() {
    SummaGoTheme {
        SummaryInfoCard(
            baseSummaryInfo = BaseSummaryInfo(
                id = 0,
                name = "Теория множеств",
                disciplineName = "Мат. анализ",
                themeName = "Theme 1",
                type = ThemeType.Lecture,
                createTime = 1728248400,
                updateTime = 1728248400
            ),
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
private fun SummariesListScreenPreview() {
    SummaGoTheme {
        SummariesListScreen(dataProvider = object : SummariesListScreenDataProvider {
            override fun getSubjects(): Flow<List<SummarySubject>> = flow {
                emit(
                    listOf(
                        SummarySubject(id = 0, name = "Мат. анализ"),
                        SummarySubject(id = 1, name = "Алг. и структуры данных"),
                        SummarySubject(id = 2, name = "История")
                    )
                )
            }

            override fun getSummaries(): Flow<List<BaseSummaryInfo>> = flow {
                emit(
                    listOf<BaseSummaryInfo>(
                        BaseSummaryInfo(
                            id = 0,
                            name = "Теория множеств",
                            disciplineName = "Мат. анализ",
                            themeName = "Theme 1",
                            type = ThemeType.Lecture,
                            createTime = 1728248400,
                            updateTime = 1728248400
                        ),
                        BaseSummaryInfo(
                            id = 0,
                            name = "Расстояние Лихтенштейна",
                            disciplineName = "Алг. и структуры данных",
                            themeName = "Theme 1",
                            type = ThemeType.Seminar,
                            createTime = 1728248400,
                            updateTime = 1728248400
                        ),
                        BaseSummaryInfo(
                            id = 0,
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

        })
    }
}
