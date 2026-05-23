package com.composables.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Stable
class TabGroupState<T> internal constructor(
    selectedTab: T,
    internal val orderedTabs: List<T>,
) {
    var selectedTab by mutableStateOf(selectedTab)
}

@Composable
fun <T> rememberTabGroupState(
    selectedTab: T,
    orderedTabs: List<T>,
): TabGroupState<T> {
    return remember(selectedTab, orderedTabs) {
        TabGroupState(
            selectedTab = selectedTab,
            orderedTabs = orderedTabs,
        )
    }
}

@Composable
fun <T> TabGroup(
    state: TabGroupState<T>,
    modifier: Modifier = Modifier,
    content: @Composable TabGroupScope<T>.() -> Unit,
) {
    TabGroup(
        selectedTab = state.selectedTab,
        onSelectedTabChange = { state.selectedTab = it },
        tabs = state.orderedTabs,
        modifier = modifier,
        content = content,
    )
}
