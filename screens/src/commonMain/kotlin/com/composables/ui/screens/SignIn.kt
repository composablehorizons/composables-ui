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
package com.composables.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonSize
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Text
import com.composables.ui.components.TextField
import com.composables.ui.theme.ComposablesTheme
import com.composables.ui.theme.Expanded
import com.composables.ui.theme.Medium
import com.composables.ui.theme.backgroundColor
import com.composables.ui.theme.colors
import com.composables.ui.theme.destructiveColor
import com.composables.ui.theme.mutedColor
import com.composables.ui.theme.onBackgroundColor
import com.composables.ui.theme.primaryColor
import com.composeunstyled.currentWindowWidthBreakpoint
import com.composeunstyled.theme.Theme

@Composable
fun SignIn() {
  ComposablesTheme {
    val emailState = rememberTextFieldState("alex@example.com")
    val passwordState = rememberTextFieldState()
    val loading = remember { mutableStateOf(false) }
    val emailError = remember { mutableStateOf<String?>(null) }
    val passwordError = remember { mutableStateOf<String?>(null) }
    val widthBreakpoint = currentWindowWidthBreakpoint()
    val contentMaxWidth = if (widthBreakpoint isAtLeast Medium) 800.dp else 420.dp
    val horizontalPadding = if (widthBreakpoint isAtLeast Medium) 40.dp else 24.dp
    val topPadding = when {
      widthBreakpoint isAtLeast Expanded -> 72.dp
      widthBreakpoint isAtLeast Medium -> 48.dp
      else -> 28.dp
    }
    val brandMarkSize = if (widthBreakpoint isAtLeast Medium) 72.dp else 64.dp

    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(Theme[colors][backgroundColor])
        .statusBarsPadding()
        .navigationBarsPadding()
        .imePadding(),
      contentAlignment = Alignment.TopCenter,
    ) {
      Column(
        modifier = Modifier
          .align(Alignment.TopCenter)
          .widthIn(max = contentMaxWidth)
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
          .padding(horizontal = horizontalPadding, vertical = topPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        SignInBrandMark(size = brandMarkSize)
        Spacer(Modifier.height(28.dp))
        Text(
          text = "Welcome back",
          color = Theme[colors][onBackgroundColor],
          fontSize = 30.sp,
          fontWeight = FontWeight.SemiBold,
          textAlign = TextAlign.Center,
        )
        Text(
          text = "Sign in to continue to your account",
          color = Theme[colors][mutedColor],
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(top = 8.dp),
        )

        Spacer(Modifier.height(36.dp))

        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
          SignInField(
            label = "Email",
            state = emailState,
            placeholder = "name@example.com",
            error = emailError.value,
          )
          SignInField(
            label = "Password",
            state = passwordState,
            placeholder = "Password",
            error = passwordError.value,
          )
        }

        Button(
          onClick = {},
          style = ButtonStyle.Link,
          modifier = Modifier.align(Alignment.End).padding(top = 10.dp),
        ) {
          Text("Forgot password?")
        }

        Spacer(Modifier.height(18.dp))

        Button(
          onClick = {},
          enabled = loading.value.not(),
          style = ButtonStyle.Primary,
          buttonSize = ButtonSize.Large,
          modifier = Modifier.fillMaxWidth(),
        ) {
          Text(if (loading.value) "Signing in..." else "Sign in")
        }

        Button(
          onClick = {},
          enabled = loading.value.not(),
          style = ButtonStyle.Outlined,
          buttonSize = ButtonSize.Large,
          modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
        ) {
          Text("Continue with Google")
        }

        Spacer(Modifier.height(40.dp))

        Row(
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Text("No account yet?", color = Theme[colors][mutedColor])
          Spacer(Modifier.width(2.dp))
          Button(
            onClick = {},
            style = ButtonStyle.Link,
            contentPadding = PaddingValues(0.dp),
          ) {
            Text("Create one")
          }
        }
      }
    }
  }
}

@Composable
private fun SignInField(
  label: String,
  state: TextFieldState,
  placeholder: String,
  error: String?,
) {
  Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
    Text(label, fontWeight = FontWeight.Medium, color = Theme[colors][onBackgroundColor])
    TextField(
      state = state,
      modifier = Modifier.fillMaxWidth(),
      accessibilityLabel = label,
      placeholder = {
        Text(placeholder)
      },
    )
    if (error != null) {
      Text(
        text = error,
        color = Theme[colors][destructiveColor],
        fontSize = 13.sp,
      )
    }
  }
}

@Composable
private fun SignInBrandMark(size: Dp) {
  Box(
    modifier = Modifier
      .size(size)
      .clip(CircleShape)
      .background(Theme[colors][primaryColor]),
    contentAlignment = Alignment.Center,
  ) {
    Text(
      text = "C",
      fontSize = 28.sp,
      fontWeight = FontWeight.SemiBold,
      color = Theme[colors][backgroundColor],
    )
  }
}
