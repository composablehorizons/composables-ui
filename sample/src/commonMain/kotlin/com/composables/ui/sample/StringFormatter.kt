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
