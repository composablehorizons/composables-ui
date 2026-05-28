package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.Text
import com.composables.ui.components.Toolbar
import com.composables.ui.theme.colors
import com.composables.ui.theme.onBackground
import com.composables.ui.theme.panel
import com.composeunstyled.theme.Theme

private val PostDetailMaxWidth = 700.dp

@Composable
fun PostDetails(
    postId: String,
    onBack: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .widthIn(max = PostDetailMaxWidth)
                .fillMaxWidth()
                .background(Theme[colors][panel])
                .align(Alignment.TopCenter),
        ) {
            Toolbar(
                backgroundColor = Theme[colors][panel],
                leading = {
                    IconButton(onClick = onBack, style = ButtonStyle.Ghost) {
                        Icon(
                            Lucide.ArrowLeft,
                            contentDescription = "Back",
                            modifier = Modifier.size(28.dp),
                            tint = Theme[colors][onBackground],
                        )
                    }
                    Text("Post")
                },
            )
            Column(
                modifier = Modifier
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
}
