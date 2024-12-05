package com.sirius.siriussummago.screens

import android.content.res.Configuration
import android.media.MediaPlayer
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.sirius.siriussummago.R
import com.sirius.siriussummago.dataModels.FullSummaryInfo
import com.sirius.siriussummago.dataModels.MaterialSummaryStatus
import com.sirius.siriussummago.dataModels.SummaryMaterial
import com.sirius.siriussummago.dataModels.SummarySubject
import com.sirius.siriussummago.dataModels.SummaryTheme
import com.sirius.siriussummago.dataModels.ThemeType
import com.sirius.siriussummago.getDateTime
import com.sirius.siriussummago.getTime
import com.sirius.siriussummago.ui.TopBar
import com.sirius.siriussummago.ui.theme.LocalDim
import com.sirius.siriussummago.ui.theme.LocalExColorScheme
import com.sirius.siriussummago.ui.theme.SummaGoTheme

@Composable
fun SummaryDetailsScreen(summaryDetails: MutableState<FullSummaryInfo?>) {
    if (summaryDetails.value == null) {
        TODO("loading screen")
    } else {
        Box{
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background)
                    .padding(horizontal = LocalDim.current.spaceMedium)
            ) {
                Spacer(modifier = Modifier.height(LocalDim.current.spaceLarge))

                TopBar(text = summaryDetails.value!!.name, iconRes = R.drawable.ic_menu, onClick = {})
                Spacer(modifier = Modifier.height(LocalDim.current.spaceLarge))

                if (summaryDetails.value!!.summaryReady) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LocalExColorScheme.current.accent.color
                        ),
                        onClick = {

                        }
                    ) {
                        Text(
                            text = stringResource(R.string.summary),
                            style = typography.titleMedium,
                            color = LocalExColorScheme.current.accent.onColor,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(LocalDim.current.spaceMedium))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .height(55.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorScheme.tertiary
                            ),
                            onClick = {

                            }
                        ) {
                            Text(
                                text = stringResource(R.string.search_in_summary),
                                style = typography.titleSmall,
                                color = colorScheme.onTertiary,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.width(LocalDim.current.spaceMedium))
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .height(55.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorScheme.inverseSurface
                            ),
                            onClick = {

                            }
                        ) {
                            Text(
                                text = stringResource(R.string.gen_questions),
                                style = typography.titleSmall,
                                color = colorScheme.inverseOnSurface,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                } else if (summaryDetails.value!!.materials.isNotEmpty()) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LocalExColorScheme.current.accent.color
                        ),
                        onClick = {

                        }
                    ) {
                        Text(
                            text = stringResource(R.string.gen_summary),
                            style = typography.titleMedium,
                            color = LocalExColorScheme.current.accent.onColor
                        )
                    }
                }



                Box { // Scroll shading box
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) { // scrollable column
                        item {
                            Spacer(modifier = Modifier.height(LocalDim.current.spaceLarge))

                            Text(
                                text = stringResource(R.string.subject_label) + ": " + summaryDetails.value!!.subject.name,
                                style = typography.bodyLarge,
                                color = colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Spacer(modifier = Modifier.width(LocalDim.current.spaceMedium))
                            Text(
                                text = stringResource(R.string.theme_label) + ": " + summaryDetails.value!!.theme.name,
                                style = typography.bodyLarge,
                                color = colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Spacer(modifier = Modifier.width(LocalDim.current.spaceMedium))
                            Text(
                                text = stringResource(R.string.date_label) + ": " + getDateTime(
                                    summaryDetails.value!!.createTime
                                ),
                                style = typography.bodyLarge,
                                color = colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )

                            Spacer(modifier = Modifier.width(LocalDim.current.spaceMedium))
                            Text(
                                text = stringResource(R.string.materials) + ":",
                                style = typography.bodyLarge,
                                color = colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Spacer(modifier = Modifier.height(LocalDim.current.spaceMedium))
                        }
                        items(summaryDetails.value!!.materials) {
                            SummaryMaterialCard(it)
                            Spacer(modifier = Modifier.height(LocalDim.current.spaceMedium))
                        }
                        item{
                            Spacer(modifier = Modifier.height(LocalDim.current.spaceExtraLarge))
                        }
                    }

                    // Shader
                    Box(
                        modifier = Modifier
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryMaterialCard(material: SummaryMaterial, mediaPlayer: MediaPlayer? = null) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.secondary)
    ) {
        when (material) {
            is SummaryMaterial.Audio -> {
                Column(
                    modifier = Modifier
                        .padding(LocalDim.current.spaceMedium)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.audio_file) + " - " + getTime(material.duration),
                        style = typography.bodyLarge,
                        color = colorScheme.onSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(modifier = Modifier.height(LocalDim.current.spaceSmall))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painterResource(R.drawable.ic_play_pause),
                            null,
                            tint = colorScheme.onSecondary,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(LocalDim.current.spaceMedium))
                        var sliderPosition = remember { mutableFloatStateOf(0f) }
                        Slider(
                            value = sliderPosition.floatValue,
                            onValueChange = { sliderPosition.floatValue = it },
                            colors = SliderDefaults.colors(
                                thumbColor = colorScheme.onPrimary,
                                activeTrackColor = colorScheme.onPrimary,
                                inactiveTrackColor = colorScheme.onSecondary,
                            )
                        )
                    }
                }
            }

            is SummaryMaterial.Text -> {
                Column(
                    modifier = Modifier
                        .padding(LocalDim.current.spaceMedium)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.document_label),
                        style = typography.bodyLarge,
                        color = colorScheme.onSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(modifier = Modifier.height(LocalDim.current.spaceSmall))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painterResource(R.drawable.ic_document),
                            null,
                            tint = colorScheme.onSecondary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(LocalDim.current.spaceSmall))
                        Text(
                            text = material.name,
                            style = typography.bodyMedium,
                            color = colorScheme.onSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }

            is SummaryMaterial.Image -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            IntrinsicSize.Min
                        )
                ) {
                    AsyncImage(
                        model = material.file, contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.55f)
                            .clip(RoundedCornerShape(24.dp))
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = stringResource(R.string.image_label),
                            style = typography.bodyMedium,
                            color = colorScheme.onSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.align(Alignment.Center)
                        )

                    }
                }
            }

            is SummaryMaterial.Video -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            IntrinsicSize.Min
                        )
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        AsyncImage(
                            model = material.file, contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth(0.55f)
                                .clip(RoundedCornerShape(24.dp))
                        )
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.ic_play_arrow),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = stringResource(R.string.video_label) + " - " + getTime(material.duration),
                            style = typography.bodyMedium,
                            color = colorScheme.onSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.align(Alignment.Center).padding(horizontal = 5.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
)
@Composable
private fun SummaryMaterialCardPreview() {
    SummaGoTheme {
        Column {
            SummaryMaterialCard(
                SummaryMaterial.Audio(
                    duration = 5127,
                    file = "",
                    loadedTime = 1728248400,
                    summaryStatus = MaterialSummaryStatus.Ready,
                    useInGeneralSummary = true
                )
            )
            Spacer(modifier = Modifier.height(LocalDim.current.spaceMedium))
            SummaryMaterialCard(
                SummaryMaterial.Text(
                    name = "sets.md",
                    file = "",
                    loadedTime = 1728248399,
                    summaryStatus = MaterialSummaryStatus.Ready,
                    useInGeneralSummary = true
                ),
//                mediaPlayer = mediaPlayer
            )
            Spacer(modifier = Modifier.height(LocalDim.current.spaceMedium))
            SummaryMaterialCard(
                SummaryMaterial.Image(
                    file = "https://i.pinimg.com/originals/8e/43/05/8e4305e5ca2524e022a75c5fdf0f1803.jpg",
                    loadedTime = 1728248398,
                    summaryStatus = MaterialSummaryStatus.Ready,
                    useInGeneralSummary = true
                ),
//                mediaPlayer = mediaPlayer
            )
            Spacer(modifier = Modifier.height(LocalDim.current.spaceMedium))
            SummaryMaterialCard(
                SummaryMaterial.Video(
                    duration = 5127,
                    file = "https://i.pinimg.com/originals/8e/43/05/8e4305e5ca2524e022a75c5fdf0f1803.jpg",
                    loadedTime = 1728248397,
                    summaryStatus = MaterialSummaryStatus.Ready,
                    useInGeneralSummary = true
                ),
//                mediaPlayer = mediaPlayer
            )
            Spacer(modifier = Modifier.height(LocalDim.current.spaceMedium))
        }
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
private fun SummaryDetailsScreenPreview() {
    SummaGoTheme {
        SummaryDetailsScreen(summaryDetails = remember {
            mutableStateOf(
                FullSummaryInfo(
                    id = 0,
                    name = "Теория множеств",
                    subject = SummarySubject(id = 0, name = "Мат. анализ"),
                    theme = SummaryTheme(id = 0, name = "Тема 1"),
                    type = ThemeType.Lecture,
                    createTime = 1728248400,
                    updateTime = 1728248400,
                    summaryReady = true,
                    materials = listOf(
                        SummaryMaterial.Audio(
                            duration = 5127,
                            file = "",
                            loadedTime = 1728248400,
                            summaryStatus = MaterialSummaryStatus.Ready,
                            useInGeneralSummary = true
                        ),
                        SummaryMaterial.Text(
                            name = "sets.md",
                            file = "",
                            loadedTime = 1728248399,
                            summaryStatus = MaterialSummaryStatus.Ready,
                            useInGeneralSummary = true
                        ),
                        SummaryMaterial.Image(
                            file = "https://i.pinimg.com/originals/8e/43/05/8e4305e5ca2524e022a75c5fdf0f1803.jpg",
                            loadedTime = 1728248398,
                            summaryStatus = MaterialSummaryStatus.Ready,
                            useInGeneralSummary = true
                        ),
                        SummaryMaterial.Video(
                            duration = 5127,
                            file = "https://i.pinimg.com/originals/8e/43/05/8e4305e5ca2524e022a75c5fdf0f1803.jpg",
                            loadedTime = 1728248397,
                            summaryStatus = MaterialSummaryStatus.Ready,
                            useInGeneralSummary = true
                        )
                    )
                )
            )
        })
    }
}
