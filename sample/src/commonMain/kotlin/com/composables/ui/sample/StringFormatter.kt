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
package com.composables.ui.sample

internal fun formatCount(count: Int): String = when {
  count >= 1_000_000 -> formatCompact(count, 1_000_000, "M")
  count >= 10_000 -> formatCompact(count, 1_000, "k")
  count >= 1_000 -> formatGrouped(count)
  else -> count.toString()
}

internal fun formatFollowerCount(count: Int): String {
  val label = if (count == 1) "follower" else "followers"
  return "${formatCount(count)} $label"
}

internal fun formatReplyCount(count: Int): String {
  val label = if (count == 1) "reply" else "replies"
  return "${formatCount(count)} $label"
}

private fun formatCompact(count: Int, unit: Int, suffix: String): String {
  val whole = count / unit
  val decimal = count % unit / (unit / 10)
  return if (decimal == 0) {
    "$whole$suffix"
  } else {
    "$whole.$decimal$suffix"
  }
}

private fun formatGrouped(count: Int): String {
  val raw = count.toString()
  val firstGroupLength = raw.length % 3
  var formatted = ""

  raw.forEachIndexed { index, digit ->
    if (index > 0 && (index - firstGroupLength).floorMod(3) == 0) {
      formatted += ","
    }
    formatted += digit
  }

  return formatted
}

private fun Int.floorMod(other: Int): Int {
  val result = this % other
  return if (result < 0) result + other else result
}
