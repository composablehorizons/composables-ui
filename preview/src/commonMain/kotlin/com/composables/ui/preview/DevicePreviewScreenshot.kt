package com.composables.ui.preview

import androidx.compose.ui.graphics.ImageBitmap

expect fun saveDevicePreviewScreenshot(image: ImageBitmap)

expect fun copyDevicePreviewScreenshotToClipboard(image: ImageBitmap)
