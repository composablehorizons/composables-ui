package com.composables.ui.sample.data

import kotlin.jvm.JvmInline

data class SocialPost(
    val id: String,
    val profileId: String,
    val author: String,
    val age: String,
    val body: String,
    val replies: String,
    val likes: String,
    val avatarUrl: String,
    val media: List<PostMedia> = emptyList(),
    val portraitMedia: Boolean = false
)

data class PostMedia(val url: String)

data class SocialProfile(
    val id: String,
    val displayName: String,
    val handle: String,
    val badge: String,
    val bio: String,
    val followerCount: String,
    val avatarUrl: String,
    val posts: List<ProfilePost>,
    val replies: List<ProfilePost>,
)

data class ProfilePost(
    val id: String,
    val age: String,
    val body: String,
    val replies: String,
    val likes: String,
    val quoteAuthor: String? = null,
    val quoteBody: String? = null,
    val quoteReplies: String? = null,
)


val feedPosts = listOf(
    SocialPost(
        id = "leg-day-mirror",
        profileId = "miaruns",
        author = "miaruns",
        age = "7m",
        body = "Leg day survived. Barely. Posting this before the stairs start collecting rent.",
        replies = "2",
        likes = "42",
        avatarUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?q=80&w=240",
        media = listOf(
            PostMedia(
                url = "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?q=80&w=1200",
            ),
            PostMedia(
                url = "https://images.unsplash.com/photo-1605296867304-46d5465a13f1?q=80&w=1200",
            ),
            PostMedia(
                url = "https://images.unsplash.com/photo-1517836357463-d25dfeac3438?q=80&w=1200",
            ),
        ),
        portraitMedia = true,
    ),
    SocialPost(
        id = "group-chat-dinner",
        profileId = "nikoafterdark",
        author = "nikoafterdark",
        age = "5m",
        body = "Group chat said \"quick dinner\" and somehow we are on round three.",
        replies = "6",
        likes = "118",
        avatarUrl = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?q=80&w=240",
        media = listOf(
            PostMedia(
                url = "https://images.unsplash.com/photo-1529156069898-49953e39b3ac?q=80&w=1200",
            ),
            PostMedia(
                url = "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?q=80&w=1200",
            ),
        ),
    ),
    SocialPost(
        id = "preworkout-truth",
        profileId = "coachcam",
        author = "coachcam",
        age = "1h",
        body = "The hardest part of training is convincing yourself the walk to the gym counts as the warmup, not the workout.",
        replies = "9",
        likes = "86",
        avatarUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?q=80&w=240",
    ),
    SocialPost(
        id = "elevator-selfie",
        profileId = "lena.jpg",
        author = "lena.jpg",
        age = "2h",
        body = "Outfit made it out of the apartment so it deserves documentation.",
        replies = "12",
        likes = "203",
        avatarUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?q=80&w=240",
        media = listOf(
            PostMedia(
                url = "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?q=80&w=1200",
            ),
            PostMedia(
                url = "https://images.unsplash.com/photo-1517841905240-472988babdf9?q=80&w=1200",
            ),
        ),
        portraitMedia = true,
    ),
    SocialPost(
        id = "saturday-market",
        profileId = "jamieoutside",
        author = "jamieoutside",
        age = "3h",
        body = "Farmers market, iced coffee, no real plan. Strong start to the weekend.",
        replies = "4",
        likes = "71",
        avatarUrl = "https://images.unsplash.com/photo-1527980965255-d3b416303d12?q=80&w=240",
        media = listOf(
            PostMedia(
                url = "https://images.unsplash.com/photo-1488459716781-31db52582fe9?q=80&w=1200",
            ),
            PostMedia(
                url = "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?q=80&w=1200",
            ),
        ),
    ),
    SocialPost(
        id = "airport-mode",
        profileId = "alexaway",
        author = "alexaway",
        age = "4h",
        body = "Airport fit, 19 tabs open, and somehow still convinced I packed the wrong charger.",
        replies = "12",
        likes = "104",
        avatarUrl = "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?q=80&w=240",
    ),
    SocialPost(
        id = "beach-roll",
        profileId = "sunnywithsam",
        author = "sunnywithsam",
        age = "5h",
        body = "Camera roll from today is 90% sky, water, and people pretending they are not sunburned.",
        replies = "6",
        likes = "167",
        avatarUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9?q=80&w=240",
        media = listOf(
            PostMedia(
                url = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?q=80&w=1200",
            ),
            PostMedia(
                url = "https://images.unsplash.com/photo-1519046904884-53103b34b206?q=80&w=1200",
            ),
            PostMedia(
                url = "https://images.unsplash.com/photo-1520454974749-611b7248ffdb?q=80&w=1200",
            ),
        ),
    ),
    SocialPost(
        id = "new-pr",
        profileId = "mileslift",
        author = "mileslift",
        age = "6h",
        body = "Hit a new PR and immediately forgot every normal way to stand for the photo.",
        replies = "3",
        likes = "129",
        avatarUrl = "https://images.unsplash.com/photo-1508214751196-bcfd4ca60f91?q=80&w=240",
        media = listOf(
            PostMedia(
                url = "https://images.unsplash.com/photo-1581009146145-b5ef050c2e1e?q=80&w=1200",
            ),
        ),
        portraitMedia = true,
    ),
    SocialPost(
        id = "late-latte",
        profileId = "softlaunch",
        author = "softlaunch",
        age = "8h",
        body = "Said I was going to work from the cafe. Mostly reviewed the playlist and called it admin.",
        replies = "15",
        likes = "138",
        avatarUrl = "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?q=80&w=240",
    ),
    SocialPost(
        id = "birthday-dump",
        profileId = "taylorposted",
        author = "taylorposted",
        age = "10h",
        body = "Birthday photo dump because posting them one at a time would be a public service failure.",
        replies = "8",
        likes = "92",
        avatarUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?q=80&w=240",
        media = listOf(
            PostMedia(
                url = "https://images.unsplash.com/photo-1527529482837-4698179dc6ce?q=80&w=1200",
            ),
            PostMedia(
                url = "https://images.unsplash.com/photo-1530103862676-de8c9debad1d?q=80&w=1200",
            ),
            PostMedia(
                url = "https://images.unsplash.com/photo-1519671482749-fd09be7ccebf?q=80&w=1200",
            ),
        ),
    ),
    SocialPost(
        id = "night-walk",
        profileId = "cityem",
        author = "cityem",
        age = "12h",
        body = "Took the long way home and the city decided to be dramatic about it.",
        replies = "2",
        likes = "77",
        avatarUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?q=80&w=240",
        media = listOf(
            PostMedia(
                url = "https://images.unsplash.com/photo-1519501025264-65ba15a82390?q=80&w=1200",
            ),
            PostMedia(
                url = "https://images.unsplash.com/photo-1494526585095-c41746248156?q=80&w=1200",
            ),
        ),
    ),
    SocialPost(
        id = "post-run",
        profileId = "5kira",
        author = "5kira",
        age = "14h",
        body = "Ran a 5K, rewarded myself with fries, achieved balance.",
        replies = "11",
        likes = "76",
        avatarUrl = "https://images.unsplash.com/photo-1547425260-76bcadfb4f2c?q=80&w=240",
    ),
)

val profiles = listOf(
    SocialProfile(
        id = "john_mobbin",
        displayName = "John",
        handle = "john_mobbin",
        badge = "social.app",
        bio = "Weekend trips, good coffee, and screenshots I swear I will organize.",
        followerCount = "1 follower",
        avatarUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?q=80&w=240",
        posts = listOf(
            ProfilePost(
                id = "coffee-follow",
                age = "4h",
                body = "Found the kind of cafe that makes you pretend you are about to write a novel.",
                replies = "2",
                likes = "11",
            ),
            ProfilePost(
                id = "morocco-dream",
                age = "4h",
                body = "Always a dream to see the Medina in Morocco!",
                quoteAuthor = "earthpix",
                quoteBody = "What is one place you're absolutely traveling to by next year?",
                quoteReplies = "237 replies",
                replies = "9",
                likes = "42",
            ),
            ProfilePost(
                id = "late-train",
                age = "7h",
                body = "The train was late but the sunset did put in a formal apology.",
                replies = "4",
                likes = "28",
            ),
            ProfilePost(
                id = "airport-window",
                age = "1d",
                body = "Window seat, tiny pretzels, and pretending turbulence is character development.",
                replies = "5",
                likes = "51",
            ),
        ),
        replies = listOf(
            ProfilePost(
                id = "reply-gym",
                age = "2h",
                body = "That post-workout walk home always feels like a side quest.",
                replies = "1",
                likes = "14",
            ),
            ProfilePost(
                id = "reply-travel",
                age = "6h",
                body = "Adding this to the travel list. The light in that courtyard looks unreal.",
                replies = "3",
                likes = "22",
                quoteAuthor = "jane_mobbin",
                quoteBody = "The quietest side street in Palermo still had the best espresso I had all year.",
                quoteReplies = "19 replies",
            ),
            ProfilePost(
                id = "reply-brunch",
                age = "1d",
                body = "Brunch only counts if someone orders one thing for the table and everyone steals it.",
                replies = "2",
                likes = "31",
            ),
        ),
    ),
    SocialProfile(
        id = "miaruns",
        displayName = "Mia",
        handle = "miaruns",
        badge = "gym log",
        bio = "Lifting, running, and documenting snacks as recovery science.",
        followerCount = "18.4k followers",
        avatarUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?q=80&w=240",
        posts = listOf(
            ProfilePost(
                "mia-leg-day",
                "7m",
                "Leg day survived. Barely. Posting this before the stairs start collecting rent.",
                "2",
                "42",
            ),
            ProfilePost("mia-run-club", "1h", "Run club pace was suspiciously close to sprint club.", "6", "33"),
            ProfilePost(
                "mia-smoothie",
                "5h",
                "Smoothie looked healthy, tasted like lawn, still drank it.",
                "8",
                "91",
            ),
        ),
        replies = listOf(
            ProfilePost("mia-reply-pr", "2h", "New PR photos have a 70% chance of awkward thumbs up.", "1", "12"),
            ProfilePost(
                "mia-reply-stretch",
                "6h",
                "Stretching for five minutes counts if I complain the whole time.",
                "3",
                "26",
            ),
        ),
    ),
    SocialProfile(
        id = "nikoafterdark",
        displayName = "Niko",
        handle = "nikoafterdark",
        badge = "social.app",
        bio = "Dinner plans, blurry nights, and making the group chat leave the house.",
        followerCount = "4,208 followers",
        avatarUrl = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?q=80&w=240",
        posts = listOf(
            ProfilePost(
                "niko-dinner",
                "5m",
                "Group chat said quick dinner and somehow we are on round three.",
                "6",
                "118"
            ),
            ProfilePost("niko-rooftop", "3h", "Rooftop lighting is doing free brand strategy for everyone.", "9", "77"),
            ProfilePost("niko-uber", "9h", "The ride home playlist deserves its own post.", "5", "48"),
        ),
        replies = listOf(
            ProfilePost(
                "niko-reply-table",
                "1h",
                "Ordering fries for the table is community leadership.",
                "4",
                "19",
            ),
            ProfilePost(
                "niko-reply-photo",
                "7h",
                "The blurry photo is always the one with the best energy.",
                "2",
                "14"
            ),
        ),
    ),
    SocialProfile(
        id = "coachcam",
        displayName = "Cam",
        handle = "coachcam",
        badge = "coach",
        bio = "Strength training, practical advice, occasional protein bar reviews.",
        followerCount = "9,812 followers",
        avatarUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?q=80&w=240",
        posts = listOf(
            ProfilePost(
                "cam-warmup",
                "1h",
                "The walk to the gym counts as the warmup emotionally, not physiologically.",
                "9",
                "86",
            ),
            ProfilePost(
                "cam-form-check",
                "4h",
                "Film your sets. Your memory is a liar with confidence.",
                "11",
                "84",
            ),
            ProfilePost(
                "cam-rest-day",
                "1d",
                "Rest day means walking to get coffee and calling it mobility.",
                "6",
                "63",
            ),
        ),
        replies = listOf(
            ProfilePost("cam-reply-strap", "3h", "Use straps when grip is the limiter, not pride.", "2", "31"),
            ProfilePost("cam-reply-sleep", "8h", "Sleep is the supplement everyone skips.", "1", "17"),
        ),
    ),
    SocialProfile(
        id = "lena.jpg",
        displayName = "Lena",
        handle = "lena.jpg",
        badge = "photo dump",
        bio = "Outfits, mirrors, city nights, and captions I rewrite six times.",
        followerCount = "2,341 followers",
        avatarUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?q=80&w=240",
        posts = listOf(
            ProfilePost(
                "lena-elevator",
                "2h",
                "Outfit made it out of the apartment so it deserves documentation.",
                "9",
                "83",
            ),
            ProfilePost(
                "lena-jacket",
                "5h",
                "Bought the jacket for one dinner and immediately made it my personality.",
                "4",
                "36"
            ),
            ProfilePost("lena-gallery", "1d", "Museum mirror pics are culture. I do not make the rules.", "7", "58"),
        ),
        replies = listOf(
            ProfilePost("lena-reply-fit", "4h", "The accessories are doing all the work and I respect it.", "5", "44"),
            ProfilePost("lena-reply-photo", "11h", "Take the photo anyway. You can overthink it later.", "2", "29"),
        ),
    ),
)

val authenticatedUser = profiles.first()

val activityEvents = listOf(
    ActivityEvent(
        id = "john-mention",
        author = profiles.random(),
        timestamp = "4h",
        type = ActivityEventType.Mention,
        body = "Here's a thread you should follow if you love botany @jane_mobbin",
    ),
    ActivityEvent(
        id = "john-reply",
        author = profiles.random(),
        timestamp = "4h",
        type = ActivityEventType.Comment,
        body = "Count me in!",
    ),
    ActivityEvent(
        id = "plantdads-follow",
        author = profiles.random(),
        timestamp = "5h",
        type = ActivityEventType.Follow,
    ),
    ActivityEvent(
        id = "plantdads-like",
        author = profiles.random(),
        timestamp = "5h",
        type = ActivityEventType.Like,
    ),
    ActivityEvent(
        id = "berryjungle-like",
        author = profiles.random(),
        timestamp = "5h",
        type = ActivityEventType.Like,
    ),
)

data class ActivityEvent(
    val id: String,
    val timestamp: String,
    val author: SocialProfile,
    val type: ActivityEventType,
    val body: String? = null,
)

@JvmInline
value class ActivityEventType private constructor(val value: Int) {
    companion object {
        val Mention = ActivityEventType(0)
        val Comment = ActivityEventType(1)
        val Follow = ActivityEventType(2)
        val Like = ActivityEventType(3)
    }
}
