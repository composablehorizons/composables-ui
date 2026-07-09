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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonSize
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.DropdownMenu
import com.composables.ui.components.DropdownMenuAlignment
import com.composables.ui.components.DropdownMenuItem
import com.composables.ui.components.DropdownMenuItemStyle
import com.composables.ui.components.DropdownMenuPanel
import com.composables.ui.components.HorizontalSeparator
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.Text
import com.composables.ui.sample.components.Avatar
import com.composables.ui.sample.components.FeedPost
import com.composables.ui.sample.components.LandscapeMediaItem
import com.composables.ui.sample.components.MediaAttachment
import com.composables.ui.sample.components.PortraitMediaItem
import com.composables.ui.sample.data.Post
import com.composables.ui.sample.data.Posts
import com.composables.ui.sample.data.UserProfiles
import com.composables.ui.sample.iconography.Ellipsis
import com.composables.ui.sample.iconography.Heart
import com.composables.ui.sample.iconography.Icons
import com.composables.ui.sample.iconography.MessageCircle
import com.composables.ui.sample.iconography.Repeat2
import com.composables.ui.theme.Medium
import com.composables.ui.theme.colors
import com.composables.ui.theme.mutedColor
import com.composables.ui.theme.onBackgroundColor
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.panelColor
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.buildModifier
import com.composeunstyled.currentWindowWidthBreakpoint
import com.composeunstyled.theme.Theme

@Composable
fun SocialFeed(
    onPostClick: (Post) -> Unit,
    onProfileClick: (String) -> Unit,
    onNewPostClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
  val posts = Posts.homeFeed()

  ProvideContentColor(Theme[colors][onPanelColor]) {
    LazyColumn(
        modifier = modifier.fillMaxSize().background(Theme[colors][panelColor]),
        contentPadding = sampleScreenContentPadding(),
    ) {
      item(key = "composer") {
        FeedComposer(
            onProfileClick = { onProfileClick(UserProfiles.johnMobbin.id) },
            onNewPostClick = onNewPostClick,
        )
        HorizontalSeparator()
      }
      itemsIndexed(
          items = posts,
          key = { _, post -> post.id },
      ) { index, post ->
        val author = UserProfiles.findWithId(post.authorId)
        val onProfileClick = { onProfileClick(author.id) }
        FeedPost(
            onClick = { onPostClick(post) },
            avatar = {
              Avatar(
                  url = author.avatarUrl,
                  modifier = Modifier.clip(CircleShape).clickable { onProfileClick() },
              )
            },
            authorName = {
              Button(onClick = onProfileClick, style = ButtonStyle.Link) {
                Text(
                    text = author.handle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
              }
            },
            timestamp = { Text(post.timestamp) },
            overflow = { PostOverflowMenu() },
            body = {
              Text(
                  text = post.body,
                  color = Theme[colors][onBackgroundColor],
              )
            },
            attachment = {
              if (post.media.isNotEmpty()) {
                MediaAttachment {
                  post.media.forEach { item ->
                    if (post.portraitMedia) {
                      PortraitMediaItem(item.url)
                    } else {
                      LandscapeMediaItem(item.url)
                    }
                  }
                }
              }
            },
            actions = { PostActions(post = post) },
        )
        if (index < posts.lastIndex) {
          HorizontalSeparator()
        }
      }
    }
  }
}

@Composable
private fun FeedComposer(
    onProfileClick: () -> Unit,
    onNewPostClick: () -> Unit,
) {
  val interactionSource = remember { MutableInteractionSource() }
  val loggedInProfile = UserProfiles.johnMobbin
  val widthBreakpoint = currentWindowWidthBreakpoint()
  val useDesktopComposer = widthBreakpoint isAtLeast Medium

  Row(
      modifier =
          Modifier.fillMaxWidth()
              .then(
                  buildModifier {
                    if (useDesktopComposer) {
                      add(Modifier.height(112.dp))
                    }
                  },
              )
              .padding(horizontal = 24.dp, vertical = if (useDesktopComposer) 0.dp else 24.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    Avatar(
        url = loggedInProfile.avatarUrl,
        modifier = Modifier.clip(CircleShape).clickable { onProfileClick() },
    )
    Text(
        text = "What's up?",
        modifier =
            Modifier.weight(1f)
                .pointerHoverIcon(PointerIcon.Text)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onNewPostClick,
                ),
        color = Theme[colors][mutedColor],
    )
    Button(
        onClick = onNewPostClick,
        style = ButtonStyle.Primary,
    ) {
      Text("Post")
    }
  }
}

@Composable
private fun PostOverflowMenu() {
  var expanded by remember { mutableStateOf(false) }

  DropdownMenu(
      expanded = expanded,
      onExpandedChange = { expanded = it },
      alignment = DropdownMenuAlignment.End,
      panel = {
        DropdownMenuPanel {
          DropdownMenuItem(onClick = {}) { Text("Save") }
          DropdownMenuItem(onClick = {}) { Text("Copy link") }
          DropdownMenuItem(onClick = {}) { Text("Mute") }
          DropdownMenuItem(onClick = {}) { Text("Not interested") }
          DropdownMenuItem(
              onClick = {},
              style = DropdownMenuItemStyle.Destructive,
          ) {
            Text("Report")
          }
        }
      },
  ) {
    IconButton(
        onClick = { expanded = expanded.not() },
        style = ButtonStyle.Ghost,
        buttonSize = ButtonSize.Small,
        indication = null,
    ) {
      Icon(
          imageVector = Icons.Ellipsis,
          contentDescription = "Post options",
          tint = Theme[colors][onBackgroundColor],
      )
    }
  }
}

@Composable
fun PostActions(post: Post, modifier: Modifier = Modifier) {
  Row(
      horizontalArrangement = Arrangement.spacedBy(6.dp),
      verticalAlignment = Alignment.CenterVertically,
      modifier = modifier,
  ) {
    ProvideContentColor(Theme[colors][mutedColor]) {
      Button(onClick = {}, style = ButtonStyle.Ghost) {
        Icon(Icons.Heart)
        Text(text = formatCount(post.likeCount))
      }

      Button(onClick = {}, style = ButtonStyle.Ghost) {
        Icon(Icons.MessageCircle)
        Text(text = formatCount(post.replyCount))
      }

      Button(onClick = {}, style = ButtonStyle.Ghost) {
        Icon(Icons.Repeat2)
        if (post.quoteReplyCount != null) {
          Text(text = formatCount(post.quoteReplyCount))
        }
      }
    }
  }
}
