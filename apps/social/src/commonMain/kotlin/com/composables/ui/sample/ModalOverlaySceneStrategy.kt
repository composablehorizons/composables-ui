/*
 * Copyright (c) 2026 Composable Horizons
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.composables.ui.sample

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavMetadataKey
import androidx.navigation3.runtime.get
import androidx.navigation3.runtime.metadata
import androidx.navigation3.scene.OverlayScene
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope
import com.composables.ui.components.AlertDialog
import com.composables.ui.components.BottomSheet
import com.composables.ui.components.BottomSheetDetent
import com.composables.ui.components.BottomSheetScrimAnimationDurationMillis
import com.composables.ui.components.rememberBottomSheetState
import com.composables.ui.theme.Large
import com.composables.ui.theme.Tall
import com.composeunstyled.currentWindowHeightBreakpoint
import com.composeunstyled.currentWindowWidthBreakpoint
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay

class ModalOverlaySceneStrategy<T : Any> : SceneStrategy<T> {

  companion object {
    fun modalOverlay(): Map<String, Any> = metadata { put(ModalOverlaySceneKey, Unit) }
  }

  override fun SceneStrategyScope<T>.calculateScene(entries: List<NavEntry<T>>): Scene<T>? {
    val lastEntry = entries.lastOrNull() ?: return null
    lastEntry.metadata[ModalOverlaySceneKey] ?: return null

    return ModalOverlayScene(
        key = lastEntry.contentKey,
        entry = lastEntry,
        previousEntries = entries.dropLast(1),
        overlaidEntries = entries.dropLast(1),
        onDismissed = onBack,
    )
  }
}

private class ModalOverlayScene<T : Any>(
    override val key: Any,
    private val entry: NavEntry<T>,
    override val previousEntries: List<NavEntry<T>>,
    override val overlaidEntries: List<NavEntry<T>>,
    private val onDismissed: () -> Unit,
) : OverlayScene<T> {
  private var dismissOverlay: (suspend () -> Unit)? = null
  private var removing = false

  override val entries: List<NavEntry<T>> = listOf(entry)

  override val content: @Composable () -> Unit = {
    val modalContent = remember(entry) { movableContentOf { entry.Content() } }

    val widthBreakpoint = currentWindowWidthBreakpoint()
    val heightBreakpoint = currentWindowHeightBreakpoint()
    val useDialog = widthBreakpoint isAtLeast Large || heightBreakpoint isAtLeast Tall

    if (useDialog) {
      AlertDialogHost(content = modalContent)
    } else {
      BottomSheetHost(content = modalContent)
    }
  }

  @Composable
  private fun BottomSheetHost(content: @Composable () -> Unit) {
    val sheetState =
        rememberBottomSheetState(
            initialDetent = BottomSheetDetent.Hidden,
        )
    dismissOverlay = { sheetState.animateTo(BottomSheetDetent.Hidden) }

    LaunchedEffect(sheetState) { sheetState.animateTo(BottomSheetDetent.FullyExpanded) }

    BottomSheet(
        state = sheetState,
        contentPadding = NoModalOverlayPadding,
        onDismissRequest = {
          if (!removing) {
            onDismissed()
          }
        },
    ) {
      content()
    }
  }

  @Composable
  private fun AlertDialogHost(content: @Composable () -> Unit) {
    val visible = remember { mutableStateOf(false) }
    dismissOverlay = { visible.value = false }

    LaunchedEffect(Unit) { visible.value = true }

    AlertDialog(
        paneTitle = "New post",
        visible = visible.value,
        contentPadding = NoModalOverlayPadding,
        onDismissRequest = {
          if (!removing) {
            onDismissed()
          }
        },
    ) {
      content()
    }
  }

  override suspend fun onRemove() {
    removing = true
    dismissOverlay?.invoke()
    // unstyled doesn't expose a way to wait for the scrim to animate away
    // so adding a delay until that is available
    delay(
        maxOf(
                BottomSheetScrimAnimationDurationMillis,
                DialogExitDurationMillis,
            )
            .milliseconds,
    )
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || this::class != other::class) return false

    other as ModalOverlayScene<*>

    return key == other.key &&
        entry == other.entry &&
        previousEntries == other.previousEntries &&
        overlaidEntries == other.overlaidEntries
  }

  override fun hashCode(): Int {
    var result = key.hashCode()
    result = 31 * result + entry.hashCode()
    result = 31 * result + previousEntries.hashCode()
    result = 31 * result + overlaidEntries.hashCode()
    return result
  }

  override fun toString(): String {
    return "ModalOverlayScene(key=$key, entry=$entry, previousEntries=$previousEntries, overlaidEntries=$overlaidEntries)"
  }
}

private const val DialogExitDurationMillis = 200
private val NoModalOverlayPadding = PaddingValues(0.dp)

object ModalOverlaySceneKey : NavMetadataKey<Unit>
