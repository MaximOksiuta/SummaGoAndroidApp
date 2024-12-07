package com.sirius.siriussummago.presentation.mdeditor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirius.siriussummago.common.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.commonmark.Extension
import org.commonmark.ext.autolink.AutolinkExtension
import org.commonmark.ext.footnotes.FootnotesExtension
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension
import org.commonmark.ext.image.attributes.ImageAttributesExtension
import org.commonmark.ext.ins.InsExtension
import org.commonmark.ext.task.list.items.TaskListItemsExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer

class NoteViewModel : ViewModel() {

    val contentState = TextFieldState()

    private lateinit var extensions: List<Extension>
    private lateinit var parser: Parser
    private lateinit var renderer: HtmlRenderer

    init {
        viewModelScope.launch(Dispatchers.IO) {
            extensions = listOf(
                TablesExtension.create(),
                AutolinkExtension.create(),
                FootnotesExtension.create(),
                HeadingAnchorExtension.create(),
                InsExtension.create(),
                ImageAttributesExtension.create(),
                StrikethroughExtension.create(),
                TaskListItemsExtension.create()
            )
            parser = Parser.builder().extensions(extensions).build()
            renderer = HtmlRenderer.builder().extensions(extensions).build()
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val html = snapshotFlow { contentState.text }
        .debounce(100)
        .mapLatest { renderer.render(parser.parse(it.toString())) }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ""
        )

    @OptIn(ExperimentalFoundationApi::class)
    fun provideBottomButtons(event: String) {
        when (event) {
            Constants.Editor.UNDO -> contentState.undoState.undo()
            Constants.Editor.REDO -> contentState.undoState.redo()
            Constants.Editor.H1 -> contentState.edit { addHeader(1) }
            Constants.Editor.H2 -> contentState.edit { addHeader(2) }
            Constants.Editor.H3 -> contentState.edit { addHeader(3) }
            Constants.Editor.H4 -> contentState.edit { addHeader(4) }
            Constants.Editor.H5 -> contentState.edit { addHeader(5) }
            Constants.Editor.H6 -> contentState.edit { addHeader(6) }
            Constants.Editor.BOLD -> contentState.edit { bold() }
            Constants.Editor.ITALIC -> contentState.edit { italic() }
            Constants.Editor.UNDERLINE -> contentState.edit { underline() }
            Constants.Editor.STRIKETHROUGH -> contentState.edit { strikeThrough() }
            Constants.Editor.MARK -> contentState.edit { mark() }
            Constants.Editor.INLINE_CODE -> contentState.edit { inlineCode() }
            Constants.Editor.INLINE_BRACKETS -> contentState.edit { inlineBrackets() }
            Constants.Editor.INLINE_BRACES -> contentState.edit { inlineBraces() }
            Constants.Editor.INLINE_MATH -> contentState.edit { inlineMath() }
            Constants.Editor.QUOTE -> contentState.edit { quote() }
            Constants.Editor.RULE -> contentState.edit { addRule() }
            Constants.Editor.DIAGRAM -> contentState.edit { addMermaid() }
            Constants.Editor.TEXT -> contentState.edit { add(event) }
        }
    }

    fun addTable(row: Int, column: Int) {
        contentState.edit { addTable(row, column) }
    }

    fun addTasks(taskList: List<TaskItem>) {
        taskList.forEach {
            contentState.edit { addTask(it.task, it.checked) }
        }
    }

}
