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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.ui.ComposablesApp
import com.composables.ui.ComposablesScreen
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonSize
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.Text
import com.composables.ui.components.TextField
import com.composables.ui.theme.Expanded
import com.composables.ui.theme.Medium
import com.composables.ui.theme.colors
import com.composables.ui.theme.destructiveColor
import com.composables.ui.theme.fieldColor
import com.composables.ui.theme.mutedColor
import com.composables.ui.theme.onBackgroundColor
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.panelColor
import com.composeunstyled.currentWindowWidthBreakpoint
import com.composeunstyled.theme.Theme

@Composable
fun SignIn() {
  ComposablesApp {
    ComposablesScreen(
      background = Theme[colors][panelColor],
      contentColor = Theme[colors][onPanelColor],
    ) {
      val emailState = rememberTextFieldState("alex@example.com")
      val passwordState = rememberTextFieldState()
      val loading = remember { mutableStateOf(false) }
      val emailError by remember { mutableStateOf<String?>(null) }
      val passwordError by remember { mutableStateOf<String?>(null) }
      var showPassword by remember { mutableStateOf(false) }
      val widthBreakpoint = currentWindowWidthBreakpoint()
      val contentMaxWidth = if (widthBreakpoint isAtLeast Medium) 600.dp else 420.dp
      val horizontalPadding = if (widthBreakpoint isAtLeast Medium) 40.dp else 24.dp
      val topPadding = when {
        widthBreakpoint isAtLeast Expanded -> 72.dp
        widthBreakpoint isAtLeast Medium -> 48.dp
        else -> 28.dp
      }

      Box(
        modifier = Modifier
          .fillMaxSize()
          .statusBarsPadding()
          .navigationBarsPadding()
          .imePadding(),
        contentAlignment = if(widthBreakpoint isAtLeast Expanded) Alignment.Center else Alignment.TopCenter,
      ) {
        Column(
          modifier =
            Modifier
              .widthIn(max = contentMaxWidth)
              .fillMaxWidth()
              .verticalScroll(rememberScrollState())
              .padding(horizontal = horizontalPadding, vertical = topPadding),
          horizontalAlignment = Alignment.Start,
        ) {
          Text(
            "Sign in",
            style = DisplayStyle
          )

          Spacer(Modifier.height(80.dp))

          Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
          ) {
            Button(
              onClick = {},
              style = ButtonStyle.Outlined,
              buttonSize = ButtonSize.Large,
              modifier = Modifier.fillMaxWidth(),
            ) {
              Icon(AppleIcon, modifier = Modifier.size(20.dp))
              Text("Sign in with Apple")
            }

            Button(
              onClick = {},
              style = ButtonStyle.Outlined,
              buttonSize = ButtonSize.Large,
              modifier = Modifier.fillMaxWidth(),
            ) {
              Icon(GoogleIcon, tint = Color.Unspecified, modifier = Modifier.size(20.dp))
              Text("Sign in with Google")
            }

            Text(
              "or",
              color = Theme[colors][mutedColor],
              textAlign = TextAlign.Center,
              modifier = Modifier.fillMaxWidth().padding(vertical = 14.dp),
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              val shape = RoundedCornerShape(16.dp)
              TextField(
                state = emailState,
                modifier = Modifier.fillMaxWidth().height(58.dp),
                accessibilityLabel = "Email",
                outputTransformation = null,
                trailing = null,
                shape = shape,
                backgroundColor = Theme[colors][fieldColor],
                placeholder = { Text("name@example.com") },
              )
              val currentEmailError = emailError
              if (currentEmailError != null) {
                Text(
                  currentEmailError,
                  color = Theme[colors][destructiveColor],
                  fontSize = 13.sp,
                )
              }
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              val shape = RoundedCornerShape(16.dp)
              TextField(
                state = passwordState,
                modifier = Modifier.fillMaxWidth().height(58.dp),
                accessibilityLabel = "Password",
                outputTransformation = if (showPassword) null else PasswordOutputTransformation,
                trailing = {
                  IconButton(
                    onClick = { showPassword = !showPassword },
                    style = ButtonStyle.Ghost,
                    buttonSize = ButtonSize.Small,
                  ) {
                    Icon(
                      if (showPassword) EyeOffIcon else EyeIcon,
                      contentDescription = if (showPassword) "Hide password" else "Show password",
                      modifier = Modifier.size(18.dp),
                      tint = Theme[colors][onBackgroundColor],
                    )
                  }
                },
                shape = shape,
                backgroundColor = Theme[colors][fieldColor],
                placeholder = { Text("Password") },
              )
              val currentPasswordError = passwordError
              if (currentPasswordError != null) {
                Text(
                  currentPasswordError,
                  color = Theme[colors][destructiveColor],
                  fontSize = 13.sp,
                )
              }
            }
          }

          Spacer(Modifier.height(24.dp))

          Button(
            onClick = {},
            enabled = loading.value.not(),
            style = ButtonStyle.Primary,
            buttonSize = ButtonSize.Large,
            modifier = Modifier.fillMaxWidth(),
          ) {
            Text(if (loading.value) "Signing in..." else "Sign in")
          }

          Spacer(Modifier.height(72.dp))

          Button(
            onClick = { /* TODO */ },
            style = ButtonStyle.Link,
            modifier = Modifier.align(Alignment.CenterHorizontally),
          ) {
            Text("Forgot password?")
          }

          Spacer(Modifier.height(12.dp))

          Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Text("No account?", color = Theme[colors][mutedColor])
            Spacer(Modifier.width(2.dp))
            Button(
              onClick = { /* TODO */ },
              style = ButtonStyle.Link,
              contentPadding = PaddingValues(0.dp),
            ) {
              Text("Sign up")
            }
          }
        }
      }
    }
  }
}

private object PasswordOutputTransformation : OutputTransformation {
  override fun TextFieldBuffer.transformOutput() {
    repeat(length) { index -> replace(index, index + 1, "\u2022") }
  }
}

val DisplayStyle: TextStyle = TextStyle(
  fontSize = 44.sp,
  fontWeight = FontWeight.Bold,
)

val AppleIcon: ImageVector
  get() {
    if (_BootstrapApple != null) return _BootstrapApple!!

    _BootstrapApple =
      ImageVector.Builder(
        name = "apple",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 16f,
        viewportHeight = 16f,
      )
        .apply {
          path(
            fill = SolidColor(Color.Black),
          ) {
            moveTo(11.182f, 0.008f)
            curveTo(11.148f, -0.03f, 9.923f, 0.023f, 8.857f, 1.18f)
            curveToRelative(-1.066f, 1.156f, -0.902f, 2.482f, -0.878f, 2.516f)
            reflectiveCurveToRelative(1.52f, 0.087f, 2.475f, -1.258f)
            reflectiveCurveToRelative(0.762f, -2.391f, 0.728f, -2.43f)
            moveToRelative(3.314f, 11.733f)
            curveToRelative(-0.048f, -0.096f, -2.325f, -1.234f, -2.113f, -3.422f)
            reflectiveCurveToRelative(1.675f, -2.789f, 1.698f, -2.854f)
            reflectiveCurveToRelative(-0.597f, -0.79f, -1.254f, -1.157f)
            arcToRelative(3.7f, 3.7f, 0f, false, false, -1.563f, -0.434f)
            curveToRelative(-0.108f, -0.003f, -0.483f, -0.095f, -1.254f, 0.116f)
            curveToRelative(-0.508f, 0.139f, -1.653f, 0.589f, -1.968f, 0.607f)
            curveToRelative(-0.316f, 0.018f, -1.256f, -0.522f, -2.267f, -0.665f)
            curveToRelative(-0.647f, -0.125f, -1.333f, 0.131f, -1.824f, 0.328f)
            curveToRelative(-0.49f, 0.196f, -1.422f, 0.754f, -2.074f, 2.237f)
            curveToRelative(-0.652f, 1.482f, -0.311f, 3.83f, -0.067f, 4.56f)
            reflectiveCurveToRelative(0.625f, 1.924f, 1.273f, 2.796f)
            curveToRelative(0.576f, 0.984f, 1.34f, 1.667f, 1.659f, 1.899f)
            reflectiveCurveToRelative(1.219f, 0.386f, 1.843f, 0.067f)
            curveToRelative(0.502f, -0.308f, 1.408f, -0.485f, 1.766f, -0.472f)
            curveToRelative(0.357f, 0.013f, 1.061f, 0.154f, 1.782f, 0.539f)
            curveToRelative(0.571f, 0.197f, 1.111f, 0.115f, 1.652f, -0.105f)
            curveToRelative(0.541f, -0.221f, 1.324f, -1.059f, 2.238f, -2.758f)
            quadToRelative(0.52f, -1.185f, 0.473f, -1.282f)
          }
          path(
            fill = SolidColor(Color.Black),
          ) {
            moveTo(11.182f, 0.008f)
            curveTo(11.148f, -0.03f, 9.923f, 0.023f, 8.857f, 1.18f)
            curveToRelative(-1.066f, 1.156f, -0.902f, 2.482f, -0.878f, 2.516f)
            reflectiveCurveToRelative(1.52f, 0.087f, 2.475f, -1.258f)
            reflectiveCurveToRelative(0.762f, -2.391f, 0.728f, -2.43f)
            moveToRelative(3.314f, 11.733f)
            curveToRelative(-0.048f, -0.096f, -2.325f, -1.234f, -2.113f, -3.422f)
            reflectiveCurveToRelative(1.675f, -2.789f, 1.698f, -2.854f)
            reflectiveCurveToRelative(-0.597f, -0.79f, -1.254f, -1.157f)
            arcToRelative(3.7f, 3.7f, 0f, false, false, -1.563f, -0.434f)
            curveToRelative(-0.108f, -0.003f, -0.483f, -0.095f, -1.254f, 0.116f)
            curveToRelative(-0.508f, 0.139f, -1.653f, 0.589f, -1.968f, 0.607f)
            curveToRelative(-0.316f, 0.018f, -1.256f, -0.522f, -2.267f, -0.665f)
            curveToRelative(-0.647f, -0.125f, -1.333f, 0.131f, -1.824f, 0.328f)
            curveToRelative(-0.49f, 0.196f, -1.422f, 0.754f, -2.074f, 2.237f)
            curveToRelative(-0.652f, 1.482f, -0.311f, 3.83f, -0.067f, 4.56f)
            reflectiveCurveToRelative(0.625f, 1.924f, 1.273f, 2.796f)
            curveToRelative(0.576f, 0.984f, 1.34f, 1.667f, 1.659f, 1.899f)
            reflectiveCurveToRelative(1.219f, 0.386f, 1.843f, 0.067f)
            curveToRelative(0.502f, -0.308f, 1.408f, -0.485f, 1.766f, -0.472f)
            curveToRelative(0.357f, 0.013f, 1.061f, 0.154f, 1.782f, 0.539f)
            curveToRelative(0.571f, 0.197f, 1.111f, 0.115f, 1.652f, -0.105f)
            curveToRelative(0.541f, -0.221f, 1.324f, -1.059f, 2.238f, -2.758f)
            quadToRelative(0.52f, -1.185f, 0.473f, -1.282f)
          }
        }
        .build()

    return _BootstrapApple!!
  }

private var _BootstrapApple: ImageVector? = null

private val GoogleIcon: ImageVector
  get() {
    if (_GoogleIcon != null) return _GoogleIcon!!

    _GoogleIcon =
      ImageVector.Builder(
        name = "google",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 18f,
        viewportHeight = 18f,
      )
        .apply {
          path(fill = SolidColor(Color(0xFF4285F4))) {
            moveTo(17.64f, 9.204f)
            curveToRelative(0f, -0.638f, -0.057f, -1.252f, -0.164f, -1.841f)
            horizontalLineTo(9f)
            verticalLineToRelative(3.482f)
            horizontalLineToRelative(4.844f)
            curveToRelative(-0.209f, 1.125f, -0.843f, 2.078f, -1.796f, 2.716f)
            verticalLineToRelative(2.258f)
            horizontalLineToRelative(2.909f)
            curveToRelative(1.702f, -1.567f, 2.683f, -3.874f, 2.683f, -6.615f)
            close()
          }
          path(fill = SolidColor(Color(0xFF34A853))) {
            moveTo(9f, 18f)
            curveToRelative(2.43f, 0f, 4.467f, -0.806f, 5.956f, -2.18f)
            lineToRelative(-2.908f, -2.259f)
            curveToRelative(-0.806f, 0.54f, -1.837f, 0.859f, -3.048f, 0.859f)
            curveToRelative(-2.344f, 0f, -4.328f, -1.583f, -5.036f, -3.711f)
            horizontalLineTo(0.957f)
            verticalLineToRelative(2.331f)
            curveTo(2.438f, 15.984f, 5.482f, 18f, 9f, 18f)
            close()
          }
          path(fill = SolidColor(Color(0xFFFBBC05))) {
            moveTo(3.964f, 10.709f)
            curveToRelative(-0.18f, -0.54f, -0.282f, -1.117f, -0.282f, -1.709f)
            reflectiveCurveToRelative(0.102f, -1.17f, 0.282f, -1.709f)
            verticalLineTo(4.96f)
            horizontalLineTo(0.957f)
            curveTo(0.347f, 6.175f, 0f, 7.549f, 0f, 9f)
            reflectiveCurveToRelative(0.348f, 2.825f, 0.957f, 4.04f)
            lineToRelative(3.007f, -2.331f)
            close()
          }
          path(fill = SolidColor(Color(0xFFEA4335))) {
            moveTo(9f, 3.58f)
            curveToRelative(1.321f, 0f, 2.508f, 0.454f, 3.44f, 1.346f)
            lineToRelative(2.581f, -2.581f)
            curveTo(13.463f, 0.892f, 11.426f, 0f, 9f, 0f)
            curveTo(5.482f, 0f, 2.438f, 2.016f, 0.957f, 4.96f)
            lineToRelative(3.007f, 2.331f)
            curveTo(4.672f, 5.163f, 6.656f, 3.58f, 9f, 3.58f)
            close()
          }
        }
        .build()

    return _GoogleIcon!!
  }

private var _GoogleIcon: ImageVector? = null

/*
 * Icons from Lucide (lucide).
 * License: ISC.
 */
private val EyeIcon: ImageVector
  get() {
    if (_EyeIcon != null) return _EyeIcon!!

    _EyeIcon =
      ImageVector.Builder(
        name = "eye",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
      )
        .apply {
          path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
          ) {
            moveTo(2.062f, 12.348f)
            arcToRelative(1f, 1f, 0f, false, true, 0f, -0.696f)
            arcToRelative(10.75f, 10.75f, 0f, false, true, 19.876f, 0f)
            arcToRelative(1f, 1f, 0f, false, true, 0f, 0.696f)
            arcToRelative(10.75f, 10.75f, 0f, false, true, -19.876f, 0f)
          }
          path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
          ) {
            moveTo(15f, 12f)
            arcTo(3f, 3f, 0f, false, true, 12f, 15f)
            arcTo(3f, 3f, 0f, false, true, 9f, 12f)
            arcTo(3f, 3f, 0f, false, true, 15f, 12f)
            close()
          }
        }
        .build()

    return _EyeIcon!!
  }

private var _EyeIcon: ImageVector? = null

private val EyeOffIcon: ImageVector
  get() {
    if (_EyeOffIcon != null) return _EyeOffIcon!!

    _EyeOffIcon =
      ImageVector.Builder(
        name = "eye-off",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
      )
        .apply {
          path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
          ) {
            moveTo(10.733f, 5.076f)
            arcToRelative(10.744f, 10.744f, 0f, false, true, 11.205f, 6.575f)
            arcToRelative(1f, 1f, 0f, false, true, 0f, 0.696f)
            arcToRelative(10.747f, 10.747f, 0f, false, true, -1.444f, 2.49f)
          }
          path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
          ) {
            moveTo(14.084f, 14.158f)
            arcToRelative(3f, 3f, 0f, false, true, -4.242f, -4.242f)
          }
          path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
          ) {
            moveTo(17.479f, 17.499f)
            arcToRelative(10.75f, 10.75f, 0f, false, true, -15.417f, -5.151f)
            arcToRelative(1f, 1f, 0f, false, true, 0f, -0.696f)
            arcToRelative(10.75f, 10.75f, 0f, false, true, 4.446f, -5.143f)
          }
          path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
          ) {
            moveTo(2f, 2f)
            lineToRelative(20f, 20f)
          }
        }
        .build()

    return _EyeOffIcon!!
  }

private var _EyeOffIcon: ImageVector? = null
