/*
 * Example demonstrating how to use custom window insets
 */

package com.composables.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

/**
 * Example implementation of custom window insets that provides your own values.
 * You can implement [CustomWindowInsets] to provide any insets you want.
 */
class GenericPhoneWindowInsets : CustomWindowInsets {
    override val captionBar: WindowInsets @Composable get() = WindowInsets(top = 24.dp)
    override val displayCutout: WindowInsets @Composable get() = WindowInsets(top = 32.dp)
    override val ime: WindowInsets @Composable get() = WindowInsets(bottom = 300.dp)
    override val mandatorySystemGestures: WindowInsets @Composable get() = WindowInsets(left = 16.dp, right = 16.dp)
    override val navigationBars: WindowInsets @Composable get() = WindowInsets(bottom = 24.dp)
    override val statusBars: WindowInsets @Composable get() = WindowInsets(top = 24.dp)
    override val systemBars: WindowInsets @Composable get() = statusBars.union(navigationBars)
    override val systemGestures: WindowInsets @Composable get() = WindowInsets(left = 8.dp, right = 8.dp)
    override val tappableElement: WindowInsets @Composable get() = WindowInsets(top = 24.dp)
    override val waterfall: WindowInsets @Composable get() = WindowInsets(0,0,0,0)
}

/**
 * Example usage of custom insets in your composable:
 *
 * ```kotlin
 * @Composable
 * fun MyApp() {
 *     val customInsets = ExampleCustomInsets()
 *     
 *     ProvideCustomWindowInsets(customInsets) {
 *         // Your content here will use the custom insets
 *         Column(
 *             modifier = Modifier
 *                 .fillMaxSize()
 *                 .statusBarsPadding() // This will use your custom status bar insets
 *         ) {
 *             Text("Content with custom insets!")
 *         }
 *     }
 * }
 * ```
 */