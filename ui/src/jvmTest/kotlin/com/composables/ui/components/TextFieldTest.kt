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
import androidx.compose.ui.awt.ComposeWindow
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.composables.ui.theme.ComposablesTheme
import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
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
        trailing = { toggle -> IconButton(onClick = toggle) { Text("toggle") } },
        decorationCenterX = 385,
    )
  }

  @Test
  fun typingCharactersOnLeadingButtonInDesktopWindowDoesNotEnterText() {
    assertTypingCharactersOnDecorationButtonDoesNotEnterText(
        leading = { toggle -> IconButton(onClick = toggle) { Text("toggle") } },
        trailing = null,
        decorationCenterX = 35,
    )
  }

  private fun assertTypingCharactersOnDecorationButtonDoesNotEnterText(
      leading: (@Composable (toggle: () -> Unit) -> Unit)?,
      trailing: (@Composable (toggle: () -> Unit) -> Unit)?,
      decorationCenterX: Int,
  ) {
    val state = TextFieldState("secret")
    var toggleCount = 0
    val toggle = { toggleCount += 1 }

    lateinit var window: ComposeWindow

    SwingUtilities.invokeAndWait {
      window = ComposeWindow()
      windows += window
      window.setSize(420, 120)
      window.setLocationRelativeTo(null)
      window.setContent {
        ComposablesTheme {
          TextField(
              state = state,
              leading = leading?.let { { it(toggle) } },
              trailing = trailing?.let { { it(toggle) } },
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

    val location = window.locationOnScreen
    robot.mouseMove(location.x + 210, location.y + 60)
    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
    robot.waitForIdle()

    robot.mouseMove(location.x + decorationCenterX, location.y + 60)
    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
    robot.waitForIdle()

    robot.keyPress(KeyEvent.VK_A)
    robot.keyRelease(KeyEvent.VK_A)
    robot.keyPress(KeyEvent.VK_B)
    robot.keyRelease(KeyEvent.VK_B)
    robot.keyPress(KeyEvent.VK_C)
    robot.keyRelease(KeyEvent.VK_C)
    robot.waitForIdle()

    assertThat(toggleCount).isEqualTo(1)
    assertThat(state.text.toString()).isEqualTo("secret")
  }
}
