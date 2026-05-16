package com.composables.one

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.one.Sample
import kotlinx.datetime.LocalTime

@Sample("TimePickerExample")
@Composable
fun TimePicker(
    time: LocalTime,
    onTimeChange: (LocalTime) -> Unit,
    use24HourFormat: Boolean = true,
    showSecondsPicker: Boolean = false,
    modifier: Modifier = Modifier,
) {
    if (use24HourFormat) {
        TimePicker24Hour(
            time = time,
            onTimeChange = onTimeChange,
            showSecondsPicker = showSecondsPicker,
            modifier = modifier
        )
    } else {
        TimePicker12Hour(time, onTimeChange, showSecondsPicker, modifier)
    }
}

@Composable
private fun TimePicker24Hour(
    time: LocalTime,
    onTimeChange: (LocalTime) -> Unit,
    showSecondsPicker: Boolean,
    modifier: Modifier = Modifier,
) {
    val currentHour by remember(time) { mutableStateOf(time.hour) }
    val currentMinute by remember(time) { mutableStateOf(time.minute) }
    val currentSecond by remember(time) { mutableStateOf(time.second) }

    val hours = (0..23).toList()
    val hourLabels = hours.map { it.toString().padStart(2, '0') }

    val minutes = (0..59).toList()
    val minuteLabels = minutes.map { it.toString().padStart(2, '0') }

    val seconds = (0..59).toList()
    val secondLabels = seconds.map { it.toString().padStart(2, '0') }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Picker(
            value = currentHour.toString().padStart(2, '0'),
            values = hourLabels,
            onValueChange = { newHour ->
                onTimeChange(LocalTime(newHour.toInt(), currentMinute, currentSecond))
            },
            modifier = Modifier.width(60.dp)
        )

        Picker(
            value = currentMinute.toString().padStart(2, '0'),
            values = minuteLabels,
            onValueChange = { newMinute ->
                onTimeChange(LocalTime(currentHour, newMinute.toInt(), currentSecond))
            },
            modifier = Modifier.width(60.dp)
        )

        if (showSecondsPicker) {
            Picker(
                value = currentSecond.toString().padStart(2, '0'),
                values = secondLabels,
                onValueChange = { newSecond ->
                    onTimeChange(LocalTime(currentHour, currentMinute, newSecond.toInt()))
                },
                modifier = Modifier.width(60.dp)
            )
        }
    }
}

@Composable
private fun TimePicker12Hour(
    time: LocalTime,
    onTimeChange: (LocalTime) -> Unit,
    showSecondsPicker: Boolean,
    modifier: Modifier = Modifier,
) {
    val currentHour by remember(time) { mutableStateOf(time.hour) }
    val currentMinute by remember(time) { mutableStateOf(time.minute) }
    val currentSecond by remember(time) { mutableStateOf(time.second) }

    val displayHour = when {
        currentHour == 0 -> 12
        currentHour > 12 -> currentHour - 12
        else -> currentHour
    }

    val isAM = currentHour < 12

    val hours = (1..12).toList()
    val hourLabels = hours.map { it.toString() }

    val minutes = (0..59).toList()
    val minuteLabels = minutes.map { it.toString().padStart(2, '0') }

    val seconds = (0..59).toList()
    val secondLabels = seconds.map { it.toString().padStart(2, '0') }

    val amPmOptions = listOf("AM", "PM")

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Picker(
            value = displayHour.toString(),
            values = hourLabels,
            onValueChange = { newHour ->
                val hour24 = when {
                    newHour.toInt() == 12 && isAM -> 0
                    newHour.toInt() == 12 && !isAM -> 12
                    !isAM -> newHour.toInt() + 12
                    else -> newHour.toInt()
                }
                onTimeChange(LocalTime(hour24, currentMinute, currentSecond))
            },
            modifier = Modifier.width(60.dp)
        )

        Picker(
            value = currentMinute.toString().padStart(2, '0'),
            values = minuteLabels,
            onValueChange = { newMinute ->
                onTimeChange(LocalTime(currentHour, newMinute.toInt(), currentSecond))
            },
            modifier = Modifier.width(60.dp)
        )

        if (showSecondsPicker) {
            Picker(
                value = currentSecond.toString().padStart(2, '0'),
                values = secondLabels,
                onValueChange = { newSecond ->
                    onTimeChange(LocalTime(currentHour, currentMinute, newSecond.toInt()))
                },
                modifier = Modifier.width(60.dp)
            )
        }

        Picker(
            value = if (isAM) "AM" else "PM",
            values = amPmOptions,
            onValueChange = { newAmPm ->
                val newHour24 = when {
                    displayHour == 12 && newAmPm == "AM" -> 0
                    displayHour == 12 && newAmPm == "PM" -> 12
                    newAmPm == "PM" -> displayHour + 12
                    else -> displayHour
                }
                onTimeChange(LocalTime(newHour24, currentMinute, currentSecond))
            },
            modifier = Modifier.width(60.dp)
        )
    }
}