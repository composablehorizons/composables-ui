package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Text
import com.composables.ui.theme.colors
import com.composables.ui.theme.onBackground
import com.composables.ui.theme.onPanel
import com.composables.ui.theme.panel
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

@Composable
fun PostDetails(
    postId: String,
) {
    ProvideContentColor(Theme[colors][onPanel]){
        Column(
            modifier = Modifier
                .background(Theme[colors][panel])
                .fillMaxWidth()
                .padding(24.dp),
        ) {
            Text(
                text = "Post details",
                style = TextStyle(fontWeight = FontWeight.Bold),
            )
            Text(
                text = "Post id: $postId",
                color = Theme[colors][onBackground],
            )
        }
    }
}
