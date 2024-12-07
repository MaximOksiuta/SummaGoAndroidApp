package com.sirius.siriussummago.presentation.mdeditor

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView


fun Int.toHexColor(): String {
    return String.format("#%06X", 0xFFFFFF and this)
//    TODO("Extract to utils")
}

@Composable
fun rememberCustomTabsIntent(): CustomTabsIntent {
    return remember {
        CustomTabsIntent.Builder()
            .setShowTitle(true)
            .build()
    }
}


data class MarkdownStyles(
    val hexTextColor: String,
    val hexCodeBackgroundColor: String,
    val hexPreBackgroundColor: String,
    val hexQuoteBackgroundColor: String,
    val hexLinkColor: String,
    val hexBorderColor: String
)

private fun createMarkdownStyles(colorScheme: ColorScheme) =
    MarkdownStyles(
        hexTextColor = colorScheme.onSurface.toArgb().toHexColor(),
        hexCodeBackgroundColor = colorScheme.surfaceVariant.toArgb().toHexColor(),
        hexPreBackgroundColor = colorScheme.surfaceColorAtElevation(1.dp).toArgb().toHexColor(),
        hexQuoteBackgroundColor = colorScheme.secondaryContainer.toArgb().toHexColor(),
        hexLinkColor = Color(red = 25, green = 118, blue = 210, alpha = 255).toArgb().toHexColor(),
        hexBorderColor = colorScheme.outline.toArgb().toHexColor()
    )

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun MarkdownText(
    html: String,
    colorScheme: ColorScheme = MaterialTheme.colorScheme
) {
    Log.d("MarkdownText", html)


    val markdownStyles = remember(colorScheme) {
        createMarkdownStyles(colorScheme)
    }

    val data by remember(html, markdownStyles) {
        mutableStateOf(
            """
                    <!DOCTYPE html>
                    <html>
                    <head>
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <script>
                        MathJax = {
                            tex: {
                                inlineMath: [['$', '$'], ['\\(', '\\)']]
                            }
                        };
                    </script>
                    <script type="text/javascript" id="MathJax-script" async
                        src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js">
                    </script>
                    <script type="module">
                      import mermaid from 'https://cdn.jsdelivr.net/npm/mermaid@11/dist/mermaid.esm.min.mjs';
                      mermaid.initialize({ startOnLoad: true });
                    </script>
                    <style type="text/css">
                        body { color: ${markdownStyles.hexTextColor}; padding: 0px; margin: 0px; }
                        a { color: ${markdownStyles.hexLinkColor}; }
                        p code { background-color: ${markdownStyles.hexCodeBackgroundColor}; padding: 4px 4px 2px 4px; margin: 4px; border-radius: 4px; }
                        td code { background-color: ${markdownStyles.hexCodeBackgroundColor}; padding: 4px 4px 2px 4px; margin: 4px; border-radius: 4px; }
                        pre { background-color: ${markdownStyles.hexPreBackgroundColor}; display: block; padding: 16px; overflow-x: auto; }
                        blockquote { border-left: 4px solid ${markdownStyles.hexQuoteBackgroundColor}; padding-left: 0px; margin-left: 0px; padding-right: 0px; margin-right: 0px; }
                        blockquote > * { margin-left: 16px; padding: 0px; }
                        blockquote blockquote { margin: 16px; }
                        table { border-collapse: collapse; display: block; white-space: nowrap; overflow-x: auto; margin-right: 1px; }
                        th, td { border: 1px solid ${markdownStyles.hexBorderColor}; padding: 6px 13px; line-height: 1.5; }
                        tr:nth-child(even) { background-color: ${markdownStyles.hexPreBackgroundColor}; }
                    </style>
                    </head>
                    <body>
                    $html
                    </body>
                    </html>
                """.trimIndent()
        )
    }

    val customTabsIntent = rememberCustomTabsIntent()

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {

            WebView(it).apply {

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest
                    ): Boolean {
                        val url = request.url.toString()
                        if (url.startsWith("http://") || url.startsWith("https://")) {
//                            customTabsIntent.launchUrl(it, Uri.parse(url))
                        }
                        return true
                    }
                }

                settings.javaScriptEnabled = true
                settings.loadsImagesAutomatically = true
                settings.defaultTextEncodingName = "UTF-8"
                isVerticalScrollBarEnabled = false
                isHorizontalScrollBarEnabled = false
                settings.setSupportZoom(true)
                settings.builtInZoomControls = true
                settings.displayZoomControls = false
                settings.useWideViewPort = false
                settings.loadWithOverviewMode = false
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
            }
        },
        update = {
            it.loadDataWithBaseURL(
                null,
                data,
                "text/html",
                "UTF-8",
                null
            )
        },
        onReset = {
            it.stopLoading()
            it.clearHistory()
        })
}
