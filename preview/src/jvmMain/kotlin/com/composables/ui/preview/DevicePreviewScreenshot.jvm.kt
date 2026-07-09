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
package com.composables.ui.preview

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toAwtImage
import java.awt.FileDialog
import java.awt.Frame
import java.awt.Image
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.imageio.ImageIO

actual fun saveDevicePreviewScreenshot(image: ImageBitmap) {
  val dialog =
      FileDialog(null as Frame?, "Save Preview Screenshot", FileDialog.SAVE).apply {
        file = defaultPreviewScreenshotFilename()
        isVisible = true
      }
  val directory = dialog.directory ?: return
  val filename = dialog.file ?: return
  val file = File(directory, filename).withPngExtension()

  ImageIO.write(image.toAwtImage(), "png", file)
}

actual fun copyDevicePreviewScreenshotToClipboard(image: ImageBitmap) {
  Toolkit.getDefaultToolkit()
      .systemClipboard
      .setContents(ImageTransferable(image.toAwtImage()), null)
}

private fun File.withPngExtension(): File {
  return if (extension.equals("png", ignoreCase = true)) {
    this
  } else {
    File(parentFile, "$name.png")
  }
}

private fun defaultPreviewScreenshotFilename(): String {
  return "Preview ${LocalDateTime.now().format(PreviewScreenshotTimestampFormatter)}.png"
}

private val PreviewScreenshotTimestampFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' HH.mm.ss")

private class ImageTransferable(
    private val image: Image,
) : Transferable {
  override fun getTransferDataFlavors(): Array<DataFlavor> {
    return arrayOf(DataFlavor.imageFlavor)
  }

  override fun isDataFlavorSupported(flavor: DataFlavor): Boolean {
    return flavor == DataFlavor.imageFlavor
  }

  override fun getTransferData(flavor: DataFlavor): Any {
    if (!isDataFlavorSupported(flavor)) {
      throw UnsupportedOperationException("Unsupported clipboard flavor: $flavor")
    }
    return image
  }
}
