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
package com.composables.ui.components

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.composables.ui.theme.ComposablesTheme
import java.awt.Robot
import java.awt.event.KeyEvent
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import javax.swing.SwingUtilities
import kotlin.test.AfterTest
import kotlin.test.Test

class TextFieldTest {
  private val windows = mutableListOf<ComposeWindow>()

  @AfterTest
  fun closeWindows() {
    windows.forEach { window -> SwingUtilities.invokeAndWait { window.dispose() } }
    windows.clear()
  }

  @Test
  fun typingCharactersOnTrailingButtonInDesktopWindowDoesNotEnterText() {
    assertTypingCharactersOnDecorationButtonDoesNotEnterText(
        leading = null,
        trailing = { modifier -> IconButton(onClick = {}, modifier = modifier) { Text("toggle") } },
    )
  }

  @Test
  fun typingCharactersOnLeadingButtonInDesktopWindowDoesNotEnterText() {
    assertTypingCharactersOnDecorationButtonDoesNotEnterText(
        leading = { modifier -> IconButton(onClick = {}, modifier = modifier) { Text("toggle") } },
        trailing = null,
    )
  }

  private fun assertTypingCharactersOnDecorationButtonDoesNotEnterText(
      leading: (@Composable (modifier: Modifier) -> Unit)?,
      trailing: (@Composable (modifier: Modifier) -> Unit)?,
  ) {
    val state = TextFieldState("secret")
    val decorationFocused = AtomicBoolean(false)
    val requestDecorationFocus = AtomicReference<() -> Unit>()

    lateinit var window: ComposeWindow

    SwingUtilities.invokeAndWait {
      window = ComposeWindow()
      windows += window
      window.setSize(420, 120)
      window.setLocationRelativeTo(null)
      window.setContent {
        val focusRequester = remember { FocusRequester() }
        requestDecorationFocus.set { focusRequester.requestFocus() }
        val decorationModifier =
            Modifier.focusRequester(focusRequester).onFocusChanged {
              decorationFocused.set(it.isFocused)
            }

        ComposablesTheme {
          TextField(
              state = state,
              leading = leading?.let { { it(decorationModifier) } },
              trailing = trailing?.let { { it(decorationModifier) } },
          )
        }
      }
      window.isVisible = true
      window.toFront()
      window.requestFocus()
    }

    val robot = Robot()
    robot.waitForIdle()
    robot.delay(250)

    SwingUtilities.invokeAndWait { awaitFocusRequester(requestDecorationFocus).invoke() }
    robot.waitForIdle()
    awaitDecorationFocus(decorationFocused)

    robot.keyPress(KeyEvent.VK_A)
    robot.keyRelease(KeyEvent.VK_A)
    robot.keyPress(KeyEvent.VK_B)
    robot.keyRelease(KeyEvent.VK_B)
    robot.keyPress(KeyEvent.VK_C)
    robot.keyRelease(KeyEvent.VK_C)
    robot.waitForIdle()

    assertThat(state.text.toString()).isEqualTo("secret")
  }

  private fun awaitFocusRequester(requestDecorationFocus: AtomicReference<() -> Unit>): () -> Unit {
    repeat(20) {
      requestDecorationFocus.get()?.let {
        return it
      }
      Thread.sleep(50)
    }
    error("Decoration button focus requester was not created")
  }

  private fun awaitDecorationFocus(decorationFocused: AtomicBoolean) {
    repeat(20) {
      if (decorationFocused.get()) {
        return
      }
      Thread.sleep(50)
    }
    error("Decoration button was not focused")
  }
}
