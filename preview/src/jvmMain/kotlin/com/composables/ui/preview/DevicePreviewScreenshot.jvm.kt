package com.composables.ui.preview

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toAwtImage
import java.awt.Image
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import java.awt.datatransfer.UnsupportedFlavorException

internal actual fun copyDevicePreviewScreenshotToClipboard(
    imageBitmap: ImageBitmap,
) {
    Toolkit.getDefaultToolkit()
        .systemClipboard
        .setContents(ImageSelection(imageBitmap.toAwtImage()), null)
}

private class ImageSelection(
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
            throw UnsupportedFlavorException(flavor)
        }
        return image
    }
}
