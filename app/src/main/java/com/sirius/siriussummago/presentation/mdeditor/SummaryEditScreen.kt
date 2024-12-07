package com.test.markdownexperements.mdeditor

import android.content.Intent
import android.provider.CalendarContract
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sirius.siriussummago.presentation.mdeditor.MarkdownText
import com.sirius.siriussummago.presentation.mdeditor.NoteEditTextField
import com.sirius.siriussummago.presentation.mdeditor.NoteEditorRow
import com.sirius.siriussummago.presentation.mdeditor.NoteViewModel
import com.sirius.siriussummago.presentation.mdeditor.add
import com.sirius.siriussummago.presentation.mdeditor.dialogs.LinkDialog
import com.sirius.siriussummago.presentation.mdeditor.dialogs.TableDialog
import com.sirius.siriussummago.presentation.mdeditor.dialogs.TaskDialog
import com.sirius.siriussummago.presentation.noRippleClickable
import com.sirius.siriussummago.presentation.ui.TopBar
import com.sirius.siriussummago.presentation.ui.theme.LocalDim
import com.sirius.siriussummago.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SummaryEditScreen(
    noteViewModel: NoteViewModel
) {
    val markdownState = noteViewModel.contentState

    LaunchedEffect(Unit) {
//        TODO("Start loading note info")
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    // Switch between read mode and edit mode
    var isReadMode by rememberSaveable { mutableStateOf(false) }
    var searchedWords by rememberSaveable { mutableStateOf("app") }

    var showTableDialog by rememberSaveable { mutableStateOf(false) }
    var showLinkDialog by rememberSaveable { mutableStateOf(false) }
    var showTaskDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isReadMode) {
        if (isReadMode) {
            keyboardController?.hide()
            focusManager.clearFocus()
        }
    }

    BackHandler(isReadMode) {
        if (isReadMode) {
            focusManager.clearFocus()
            isReadMode = false
        }
    }

    var isTitleFocused by rememberSaveable { mutableStateOf(false) }
    var isContentFocused by rememberSaveable { mutableStateOf(false) }

    BackHandler(isTitleFocused || isContentFocused) {
        focusManager.clearFocus()
        keyboardController?.hide()
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        onDispose {
//            TODO("Save note")
        }
    }

//    TODO("Good feature, we need check the note has been saved")

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Конспект",
                        maxLines = 1,
                        modifier = Modifier.basicMarquee()
                    )
                },
                actions = {

                    IconButton(onClick = {

                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Save,
                            contentDescription = "Save"
                        )
                    }

                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                        tooltip = {
                            PlainTooltip(
                                content = { Text("Ctrl + P") }
                            )
                        },
                        state = rememberTooltipState(),
                        focusable = false,
                        enableUserInput = true
                    ) {
                        IconButton(onClick = { isReadMode = !isReadMode }) {
                            Icon(
                                imageVector = if (!isReadMode) Icons.AutoMirrored.Outlined.MenuBook
                                else Icons.Outlined.EditNote,
                                contentDescription = "Mode"
                            )
                        }
                    }

                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = "More"
                        )
                    }
                    val showMenu = remember { mutableStateOf(false) }

                    DropdownMenu(
                        expanded = showMenu.value,
                        onDismissRequest = { showMenu.value = false }
                    ) {

                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "Delete"
                                )
                            },
                            text = { Text(text = stringResource(id = R.string.delete)) },
                            onClick = { })

                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Upload,
                                    contentDescription = "Export"
                                )
                            },
                            text = { Text(text = stringResource(R.string.export)) },
                            onClick = { })

                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Share,
                                    contentDescription = "Share"
                                )
                            },
                            text = { Text(text = stringResource(R.string.share)) },
                            onClick = { })
                    }
                })
        },
        bottomBar = {
            AnimatedVisibility(
                visible = !isReadMode,
                enter = slideInVertically { fullHeight -> fullHeight },
                exit = slideOutVertically { fullHeight -> fullHeight }) {
                NoteEditorRow(
                    canRedo = noteViewModel.contentState.undoState.canRedo,
                    canUndo = noteViewModel.contentState.undoState.canUndo,
                    onEdit = {
                        noteViewModel.provideBottomButtons(it)
                    },
                    onTableButtonClick = { showTableDialog = true },
                    onTaskButtonClick = { showTaskDialog = true },
                    onLinkButtonClick = { showLinkDialog = true })
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            val titleState = rememberTextFieldState()
//            TODO("extract title state")
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isTitleFocused = it.isFocused },
                state = titleState,
                readOnly = isReadMode,
//                lineLimits = TextFieldLineLimits.SingleLine,
                textStyle = MaterialTheme.typography.headlineLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                decorator = {
                    Box {
                        if (titleState.text.isEmpty()) {
                            Text(
                                text = "Название конспекта",
                                style = MaterialTheme.typography.headlineLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                            )
                        }
                        it()
                    }
                }
            )
//            TODO("show file info")


            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            if (isReadMode) {
                MarkdownText(html = noteViewModel.html.collectAsStateWithLifecycle("").value)
            } else {
                NoteEditTextField(
                    modifier = Modifier.fillMaxSize(),
                    state = markdownState,
                    searchedWords = searchedWords,
                    readMode = isReadMode,
                    onScanButtonClick = {},
                    onTableButtonClick = { showTableDialog = true },
                    onTaskButtonClick = { showTaskDialog = true },
                    onLinkButtonClick = { showLinkDialog = true },
                    onPreviewButtonClick = { isReadMode = !isReadMode },
                    onFocusChanged = { isContentFocused = it }
                )
            }
        }
    }

    if (showTableDialog) {
        TableDialog(onDismissRequest = { showTableDialog = false }) { row, column ->
            noteViewModel.addTable(row, column)
        }
    }

    if (showTaskDialog) {
        TaskDialog(onDismissRequest = { showTaskDialog = false }) {
            noteViewModel.addTasks(it)
        }
    }

    if (showLinkDialog) {
        LinkDialog(onDismissRequest = { showLinkDialog = false }) { name, uri ->
            val insertText = "[${name}](${uri})"
            noteViewModel.contentState.edit { add(insertText) }
        }
    }
}
