@file:OptIn(ExperimentalWasmJsInterop::class, ExperimentalComposeUiApi::class)

package com.composables.ui.demo

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.window.ComposeViewport
import com.composables.ui.demo.generated.resources.Inter
import com.composables.ui.demo.generated.resources.Res
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideTextStyle
import kotlinx.browser.document
import org.jetbrains.compose.resources.Font
import org.w3c.dom.url.URLSearchParams

fun main() {
    val params = URLSearchParams(document.location?.search?.toJsString())
    val initialDemoId = params.get("id")

    ComposeViewport {
        val inter = FontFamily(Font(Res.font.Inter))
        ProvideTextStyle(LocalTextStyle.current.merge(TextStyle(fontFamily = inter))) {
            Demo(initialDemoId = initialDemoId)
        }
    }
}
