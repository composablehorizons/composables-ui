package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.one.components.TimePicker
import com.composables.one.styling.textStyles
import com.composables.one.styling.title
import com.composeunstyled.UnstyledText
import com.composeunstyled.theme.Theme
import kotlinx.datetime.LocalTime

@Composable
fun TimePickerExample() {
    var time by remember { mutableStateOf(LocalTime(12, 0)) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        UnstyledText("Set Alarm", style = Theme[textStyles][title])
        Spacer(Modifier.height(18.dp))
        TimePicker(
            time = time,
            onTimeChange = { time = it },
            use24HourFormat = false,
            showSecondsPicker = true,
        )
    }
}