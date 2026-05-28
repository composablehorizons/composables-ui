package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ChevronRight
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.X
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonSize
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.HorizontalSeparator
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.Text
import com.composables.ui.components.TextField
import com.composables.ui.sample.components.Avatar
import com.composables.ui.theme.Medium
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composables.ui.theme.field
import com.composables.ui.theme.muted
import com.composables.ui.theme.onBackground
import com.composables.ui.theme.panel
import com.composeunstyled.currentWidthBreakpoint
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme

@Composable
fun Search(
    modifier: Modifier = Modifier,
) {
    val widthBreakpoint = currentWidthBreakpoint()
    val showPanelOutline = widthBreakpoint isAtLeast Medium
    val panelShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .then(
                    if (showPanelOutline) {
                        Modifier
                            .background(Theme[colors][panel], panelShape)
                            .outline(
                                width = 1.dp,
                                color = Theme[colors][border],
                                shape = panelShape,
                                offset = (-1).dp,
                            )
                            .clip(panelShape)
                    } else {
                        Modifier.background(Theme[colors][panel])
                    },
                )
                .align(Alignment.TopCenter),
        ) {
            if (!showPanelOutline) {
                SearchHeader()
            }
            SearchResults()
        }
    }
}

@Composable
private fun SearchHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Search",
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun SearchResults() {
    val queryState = rememberTextFieldState(initialText = "nasa")
    val query = queryState.text.toString()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 96.dp),
    ) {
        item {
            TextField(
                state = queryState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                accessibilityLabel = "Search",
                placeholder = { Text("Search") },
                shape = CircleShape,
                backgroundColor = Theme[colors][field],
                borderColor = Theme[colors][border],
                minHeight = 48.dp,
                leading = {
                    Icon(
                        imageVector = Lucide.Search,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Theme[colors][muted],
                    )
                },
                trailing = {
                    if (query.isNotEmpty()) {
                        IconButton(
                            onClick = { queryState.clearText() },
                            modifier = Modifier.size(32.dp),
                            style = ButtonStyle.Ghost,
                            buttonSize = ButtonSize.Small,
                        ) {
                            Icon(
                                imageVector = Lucide.X,
                                contentDescription = "Clear search",
                                modifier = Modifier.size(15.dp),
                                tint = Theme[colors][muted],
                            )
                        }
                    }
                },
            )
        }

        SearchSuggestions.forEach { suggestion ->
            item(key = "suggestion-$suggestion") {
                SearchSuggestionRow(suggestion)
                HorizontalSeparator(modifier = Modifier.padding(start = 72.dp))
            }
        }

        SearchProfiles.forEachIndexed { index, profile ->
            item(key = profile.handle) {
                SearchProfileRow(profile)
                if (index < SearchProfiles.lastIndex) {
                    HorizontalSeparator(modifier = Modifier.padding(start = 72.dp))
                }
            }
        }
    }
}

@Composable
private fun SearchSuggestionRow(
    suggestion: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Lucide.Search,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = Theme[colors][muted],
        )
        Spacer(Modifier.width(24.dp))
        Text(
            text = suggestion,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Icon(
            imageVector = Lucide.ChevronRight,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Theme[colors][muted],
        )
    }
}

@Composable
private fun SearchProfileRow(
    profile: SearchProfile,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Avatar(
            url = profile.avatarUrl,
            size = 44,
        )
        Spacer(Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = profile.handle,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = profile.name,
                color = Theme[colors][muted],
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = profile.followers,
                color = Theme[colors][onBackground],
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(Modifier.width(16.dp))
        Button(
            onClick = { },
            modifier = Modifier.width(104.dp),
            style = ButtonStyle.Outlined,
            buttonSize = ButtonSize.Small,
            shape = RoundedCornerShape(12.dp),
        ) {
            Text("Follow")
        }
    }
}

private data class SearchProfile(
    val handle: String,
    val name: String,
    val followers: String,
    val avatarUrl: String,
)

private val SearchSuggestions = listOf(
    "nasa",
    "nasa webb telescope",
    "nasab",
)

private val SearchProfiles = listOf(
    SearchProfile(
        handle = "nasatpute",
        name = "Nisha Satpute",
        followers = "137 followers",
        avatarUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?q=80&w=240",
    ),
    SearchProfile(
        handle = "a_f_r_o_d_i_t_e__7w",
        name = "Nasa",
        followers = "1,132 followers",
        avatarUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9?q=80&w=240",
    ),
    SearchProfile(
        handle = "nasaan",
        name = "nasaan",
        followers = "3,401 followers",
        avatarUrl = "https://images.unsplash.com/photo-1508214751196-bcfd4ca60f91?q=80&w=240",
    ),
    SearchProfile(
        handle = "nasa_farsi",
        name = "Nasa Farsi",
        followers = "9,502 followers",
        avatarUrl = "https://images.unsplash.com/photo-1527980965255-d3b416303d12?q=80&w=240",
    ),
    SearchProfile(
        handle = "nasai.co.kr",
        name = "Nasai",
        followers = "31 followers",
        avatarUrl = "https://images.unsplash.com/photo-1547425260-76bcadfb4f2c?q=80&w=240",
    ),
    SearchProfile(
        handle = "nasa.truck",
        name = "Nasa Truck",
        followers = "389 followers",
        avatarUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2c?q=80&w=240",
    ),
)
