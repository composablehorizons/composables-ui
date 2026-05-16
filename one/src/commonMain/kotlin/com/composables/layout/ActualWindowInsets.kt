/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.composables.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

private val ZeroInsets = WindowInsets(0, 0, 0, 0)

/**
 * CompositionLocal that provides custom insets overrides.
 * Set this to provide your own insets implementations.
 */
val LocalCustomWindowInsets = compositionLocalOf<CustomWindowInsets?> { null }

/**
 * Interface for providing custom window insets implementations.
 * Implement this to provide your own insets values.
 */
interface CustomWindowInsets {
    val captionBar: WindowInsets @Composable get
    val displayCutout: WindowInsets @Composable get
    val ime: WindowInsets @Composable get
    val mandatorySystemGestures: WindowInsets @Composable get
    val navigationBars: WindowInsets @Composable get
    val statusBars: WindowInsets @Composable get
    val systemBars: WindowInsets @Composable get
    val systemGestures: WindowInsets @Composable get
    val tappableElement: WindowInsets @Composable get
    val waterfall: WindowInsets @Composable get
//    val safeDrawing: WindowInsets @Composable get
//    val safeGestures: WindowInsets @Composable get
//    val safeContent: WindowInsets @Composable get
}

/**
 * Composable function to provide custom window insets.
 * Use this to override the default insets behavior.
 * 
 * @param customInsets Your custom insets implementation
 * @param content The content that will use the custom insets
 */
@Composable
fun ProvideWindowInsets(
    customInsets: CustomWindowInsets,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalCustomWindowInsets provides customInsets,
        content = content
    )
}

/**
 * An insets type representing the window of a caption bar.
 * It is useless for iOS.
 */
val WindowInsets.Companion.captionBar: WindowInsets
    @Composable
    get() = LocalCustomWindowInsets.current?.captionBar ?: ZeroInsets

/**
 * This [WindowInsets] represents the area with the display cutout (e.g. for camera).
 * On desktop, this is typically zero.
 */
val WindowInsets.Companion.displayCutout: WindowInsets
    @Composable
    get() = LocalCustomWindowInsets.current?.displayCutout ?: ZeroInsets

/**
 * An insets type representing the window of an "input method",
 * typically the software keyboard. On desktop, this is usually zero.
 */
val WindowInsets.Companion.ime: WindowInsets
    @Composable
    get() = LocalCustomWindowInsets.current?.ime ?: ZeroInsets

/**
 * These insets represent the space where system gestures have priority over application gestures.
 * On desktop, this is typically zero.
 */
val WindowInsets.Companion.mandatorySystemGestures: WindowInsets
    @Composable
    get() = LocalCustomWindowInsets.current?.mandatorySystemGestures ?: ZeroInsets

/**
 * These insets represent where system UI places navigation bars.
 * Interactive UI should avoid the navigation bars area.
 * On desktop, this is typically zero.
 */
val WindowInsets.Companion.navigationBars: WindowInsets
    @Composable
    get() = LocalCustomWindowInsets.current?.navigationBars ?: ZeroInsets

/**
 * These insets represent status bar.
 * On desktop, this is typically zero.
 */
val WindowInsets.Companion.statusBars: WindowInsets
    @Composable
    get() = LocalCustomWindowInsets.current?.statusBars ?: ZeroInsets

/**
 * These insets represent all system bars.
 * Includes [statusBars], [captionBar] as well as [navigationBars], but not [ime].
 * On desktop, this is typically zero.
 */
val WindowInsets.Companion.systemBars: WindowInsets
    @Composable
    get() = LocalCustomWindowInsets.current?.systemBars ?: ZeroInsets

/**
 * The [systemGestures] insets represent the area of a window where system gestures have
 * priority and may consume some or all touch input, e.g. due to the system bar
 * occupying it, or it being reserved for touch-only gestures.
 * On desktop, this is typically zero.
 */
val WindowInsets.Companion.systemGestures: WindowInsets
    @Composable
    get() = LocalCustomWindowInsets.current?.systemGestures ?: ZeroInsets

/**
 * Returns the tappable element insets.
 * On desktop, this is typically zero.
 */
val WindowInsets.Companion.tappableElement: WindowInsets
    @Composable
    get() = LocalCustomWindowInsets.current?.tappableElement ?: ZeroInsets

/**
 * The insets for the curved areas in a waterfall display.
 * It is useless for iOS.
 */
val WindowInsets.Companion.waterfall: WindowInsets
    @Composable
    get() = LocalCustomWindowInsets.current?.waterfall ?: ZeroInsets

/**
 * The insets that include areas where content may be covered by other drawn content.
 * This includes all [systemBars], [displayCutout], and [ime].
 */
val WindowInsets.Companion.safeDrawing: WindowInsets
    @Composable
    get() = systemBars.union(ime).union(displayCutout)

/**
 * The insets that include areas where gestures may be confused with other input,
 * including [systemGestures], [mandatorySystemGestures], [waterfall], and [tappableElement].
 */
val WindowInsets.Companion.safeGestures: WindowInsets
    @Composable
    get() =  tappableElement.union(mandatorySystemGestures).union(systemGestures).union(waterfall)

/**
 * The insets that include all areas that may be drawn over or have gesture confusion,
 * including everything in [safeDrawing] and [safeGestures].
 */
val WindowInsets.Companion.safeContent: WindowInsets
    @Composable
    get() =  safeDrawing.union(safeGestures)