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
    val dialog = FileDialog(null as Frame?, "Save Preview Screenshot", FileDialog.SAVE).apply {
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

private val PreviewScreenshotTimestampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' HH.mm.ss")

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
