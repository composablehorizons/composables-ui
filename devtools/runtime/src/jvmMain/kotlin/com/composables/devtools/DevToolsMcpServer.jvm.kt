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

import java.io.BufferedInputStream
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.Base64

fun main() {
  DevToolsMcpServer().run()
}

private class DevToolsMcpServer {
  private val input = BufferedInputStream(System.`in`)
  private val output = System.out
  private val httpClient = HttpClient.newHttpClient()

  fun run() {
    while (true) {
      val message = readMessage() ?: return
      val id = message.jsonValue("id")
      val method = message.jsonValue("method") ?: continue

      when (method) {
        "initialize" ->
            respond(
                id = id,
                result =
                    """
                    {"protocolVersion":"2024-11-05","capabilities":{"tools":{}},"serverInfo":{"name":"composables-devtools","version":"0.1.0"}}
                    """
                        .trimIndent(),
            )
        "notifications/initialized" -> continue
        "tools/list" -> respond(id = id, result = toolsListJson)
        "tools/call" -> handleToolCall(id, message)
        "ping" -> respond(id = id, result = "{}")
        else ->
            if (id != null)
                respondError(id = id, code = -32601, message = "Unknown method: $method")
      }
    }
  }

  private fun handleToolCall(id: String?, message: String) {
    when (message.jsonValue("name")) {
      "status" -> {
        val ready = runCatching { readStatusReady() }.getOrDefault(false)
        respond(id = id, result = """{"content":[{"type":"text","text":"{\"ready\":$ready}"}]}""")
      }
      "take_screenshot" -> {
        val screenshot =
            runCatching { requestScreenshot() }
                .getOrElse { error ->
                  respondError(
                      id = id, code = -32000, message = error.message ?: "Screenshot failed")
                  return
                }
        val saveTo = message.jsonValue("save_to")
        if (saveTo != null) File(saveTo).writeBytes(screenshot)
        val encoded = Base64.getEncoder().encodeToString(screenshot)
        val savedText =
            if (saveTo == null) {
              "Screenshot captured"
            } else {
              "Screenshot saved to: $saveTo"
            }
        respond(
            id = id,
            result =
                """
                {"content":[{"type":"text","text":"${savedText.escapeJson()}"},{"type":"image","data":"$encoded","mimeType":"image/png"}]}
                """
                    .trimIndent(),
        )
      }
      else -> respondError(id = id, code = -32602, message = "Unknown tool")
    }
  }

  private fun readStatusReady(): Boolean {
    val port = readPort()
    val request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:$port/status")).GET().build()
    val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    return response.statusCode() == 200 && response.body().contains(""""ready":true""")
  }

  private fun requestScreenshot(): ByteArray {
    val port = readPort()
    val request =
        HttpRequest.newBuilder(URI.create("http://127.0.0.1:$port/screenshot")).GET().build()
    val response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray())
    check(response.statusCode() == 200) { "DevTools screenshot is not ready" }
    return response.body()
  }

  private fun readPort(): Int {
    val portFile = defaultDevToolsAutomationPortFile()
    check(portFile.exists()) { "DevTools automation port file does not exist: ${portFile.path}" }
    return portFile.readText().trim().toInt()
  }

  private fun readMessage(): String? {
    var contentLength: Int? = null
    while (true) {
      val line = input.readAsciiLine() ?: return null
      if (line.isEmpty()) break
      val separator = line.indexOf(':')
      if (separator > 0 &&
          line.substring(0, separator).equals("Content-Length", ignoreCase = true)) {
        contentLength = line.substring(separator + 1).trim().toInt()
      }
    }
    val length = contentLength ?: return null
    return input.readNBytes(length).toString(Charsets.UTF_8)
  }

  private fun respond(id: String?, result: String) {
    write("""{"jsonrpc":"2.0","id":${id ?: "null"},"result":$result}""")
  }

  private fun respondError(id: String?, code: Int, message: String) {
    write(
        """{"jsonrpc":"2.0","id":${id ?: "null"},"error":{"code":$code,"message":"${message.escapeJson()}"}}""")
  }

  private fun write(message: String) {
    val bytes = message.toByteArray(Charsets.UTF_8)
    output.write("Content-Length: ${bytes.size}\r\n\r\n".toByteArray(Charsets.US_ASCII))
    output.write(bytes)
    output.flush()
  }
}

private fun BufferedInputStream.readAsciiLine(): String? {
  val bytes = mutableListOf<Byte>()
  while (true) {
    val value = read()
    if (value == -1)
        return if (bytes.isEmpty()) null else bytes.toByteArray().toString(Charsets.US_ASCII)
    if (value == '\n'.code) {
      if (bytes.lastOrNull() == '\r'.code.toByte()) bytes.removeAt(bytes.lastIndex)
      return bytes.toByteArray().toString(Charsets.US_ASCII)
    }
    bytes += value.toByte()
  }
}

private fun String.jsonValue(name: String): String? {
  val pattern = Regex(""""${Regex.escape(name)}"\s*:\s*("([^"\\]*(?:\\.[^"\\]*)*)"|-?\d+)""")
  val match = pattern.find(this) ?: return null
  val raw = match.groupValues[1]
  return if (raw.startsWith("\"")) raw.substring(1, raw.length - 1).unescapeJson() else raw
}

private fun String.escapeJson(): String {
  return buildString {
    for (char in this@escapeJson) {
      when (char) {
        '\\' -> append("""\\""")
        '"' -> append("\\\"")
        '\n' -> append("\\n")
        '\r' -> append("\\r")
        '\t' -> append("\\t")
        else -> append(char)
      }
    }
  }
}

private fun String.unescapeJson(): String {
  return replace("\\\"", "\"").replace("\\\\", "\\")
}

private val toolsListJson =
    """
    {"tools":[
      {"name":"status","description":"Reports whether the Composables DevTools host is reachable and has a screenshot ready.","inputSchema":{"type":"object","properties":{}}},
      {"name":"take_screenshot","description":"Captures the current Composables DevTools preview as a PNG without using desktop screen capture.","inputSchema":{"type":"object","properties":{"save_to":{"type":"string","description":"Optional absolute path where the PNG should be written."}}}}
    ]}
    """
        .trimIndent()
