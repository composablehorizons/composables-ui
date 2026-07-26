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
package com.composables.devtools

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import java.io.File
import java.net.InetAddress
import java.net.InetSocketAddress
import java.util.concurrent.Executors

private var devToolsAutomationServer: HttpServer? = null

fun startDevToolsAutomationServer(
    portFile: File = defaultDevToolsAutomationPortFile(),
) {
  if (devToolsAutomationServer != null) return

  val server = HttpServer.create(InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0)
  server.createContext("/status") { exchange ->
    exchange.respondText("""{"ready":${captureDevToolsScreenshotPng() != null}}""")
  }
  server.createContext("/screenshot") { exchange ->
    val screenshot = captureDevToolsScreenshotPng()
    if (screenshot == null) {
      exchange.respondText("""{"error":"screenshot_not_ready"}""", code = 409)
    } else {
      exchange.responseHeaders.add("Content-Type", "image/png")
      exchange.sendResponseHeaders(200, screenshot.size.toLong())
      exchange.responseBody.use { it.write(screenshot) }
    }
  }
  server.executor =
      Executors.newSingleThreadExecutor { runnable ->
        Thread(runnable, "Composables DevTools Automation").apply { isDaemon = true }
      }
  server.start()

  portFile.parentFile?.mkdirs()
  portFile.writeText("${server.address.port}\n")
  devToolsAutomationServer = server
}

internal fun defaultDevToolsAutomationPortFile(): File {
  val configuredPath = System.getProperty("composables.devtools.portFile")
  return if (configuredPath.isNullOrBlank()) {
    File(System.getProperty("java.io.tmpdir"), "composables-devtools.port")
  } else {
    File(configuredPath)
  }
}

private fun HttpExchange.respondText(text: String, code: Int = 200) {
  val bytes = text.toByteArray(Charsets.UTF_8)
  responseHeaders.add("Content-Type", "application/json; charset=utf-8")
  sendResponseHeaders(code, bytes.size.toLong())
  responseBody.use { it.write(bytes) }
}
