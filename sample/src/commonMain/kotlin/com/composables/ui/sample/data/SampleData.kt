package com.composables.ui.sample.data

import kotlin.jvm.JvmInline

typealias ProfileId = String
typealias PostId = String

data class UserProfile(
    val id: ProfileId,
    val displayName: String,
    val handle: String,
    val badge: String,
    val bio: String,
    val followerCount: Int,
    val avatarUrl: String,
)

data class Post(
    val id: PostId,
    val authorId: ProfileId,
    val timestamp: String,
    val body: String,
    val replyCount: Int,
    val likeCount: Int,
    val media: List<PostMedia> = emptyList(),
    val portraitMedia: Boolean = false,
    val quoteAuthor: String? = null,
    val quoteBody: String? = null,
    val quoteReplyCount: Int? = null,
)

data class PostMedia(val url: String)

object UserProfiles {
    val johnMobbin = UserProfile(
        id = "john_mobbin",
        displayName = "John",
        handle = "john_mobbin",
        badge = "social.app",
        bio = "Weekend trips, good coffee, and screenshots I swear I will organize.",
        followerCount = 1,
        avatarUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?q=80&w=240",
    )
    val miaRuns = UserProfile(
        id = "miaruns",
        displayName = "Mia",
        handle = "miaruns",
        badge = "gym log",
        bio = "Lifting, running, and documenting snacks as recovery science.",
        followerCount = 18_400,
        avatarUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?q=80&w=240",
    )
    val nikoAfterDark = UserProfile(
        id = "nikoafterdark",
        displayName = "Niko",
        handle = "nikoafterdark",
        badge = "social.app",
        bio = "Dinner plans, blurry nights, and making the group chat leave the house.",
        followerCount = 4_208,
        avatarUrl = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?q=80&w=240",
    )
    val coachCam = UserProfile(
        id = "coachcam",
        displayName = "Cam",
        handle = "coachcam",
        badge = "coach",
        bio = "Strength training, practical advice, occasional protein bar reviews.",
        followerCount = 9_812,
        avatarUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?q=80&w=240",
    )
    val lenaJpg = UserProfile(
        id = "lena.jpg",
        displayName = "Lena",
        handle = "lena.jpg",
        badge = "photo dump",
        bio = "Outfits, mirrors, city nights, and captions I rewrite six times.",
        followerCount = 2_341,
        avatarUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?q=80&w=240",
    )
    val jamieOutside = UserProfile(
        id = "jamieoutside",
        displayName = "Jamie",
        handle = "jamieoutside",
        badge = "outside",
        bio = "Farmers markets, long walks, and no fixed plan.",
        followerCount = 6_443,
        avatarUrl = "https://images.unsplash.com/photo-1527980965255-d3b416303d12?q=80&w=240",
    )
    val alexAway = UserProfile(
        id = "alexaway",
        displayName = "Alex",
        handle = "alexaway",
        badge = "travel",
        bio = "Airport fits and overpacked carry-ons.",
        followerCount = 12_700,
        avatarUrl = "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?q=80&w=240",
    )
    val sunnyWithSam = UserProfile(
        id = "sunnywithsam",
        displayName = "Sam",
        handle = "sunnywithsam",
        badge = "beach roll",
        bio = "Sky, water, SPF, repeat.",
        followerCount = 8_104,
        avatarUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9?q=80&w=240",
    )
    val milesLift = UserProfile(
        id = "mileslift",
        displayName = "Miles",
        handle = "mileslift",
        badge = "power",
        bio = "PRs, bad poses, good training blocks.",
        followerCount = 13_200,
        avatarUrl = "https://images.unsplash.com/photo-1508214751196-bcfd4ca60f91?q=80&w=240",
    )
    val softLaunch = UserProfile(
        id = "softlaunch",
        displayName = "Sofia",
        handle = "softlaunch",
        badge = "cafe desk",
        bio = "Cafe work sessions and playlist reviews.",
        followerCount = 3_902,
        avatarUrl = "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?q=80&w=240",
    )
    val taylorPosted = UserProfile(
        id = "taylorposted",
        displayName = "Taylor",
        handle = "taylorposted",
        badge = "photo dump",
        bio = "The camera roll is the archive.",
        followerCount = 5_018,
        avatarUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?q=80&w=240",
    )
    val cityEm = UserProfile(
        id = "cityem",
        displayName = "Em",
        handle = "cityem",
        badge = "city walks",
        bio = "Long way home, always.",
        followerCount = 7_621,
        avatarUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?q=80&w=240",
    )
    val kira5k = UserProfile(
        id = "5kira",
        displayName = "Kira",
        handle = "5kira",
        badge = "run log",
        bio = "Runs, fries, balance.",
        followerCount = 2_819,
        avatarUrl = "https://images.unsplash.com/photo-1547425260-76bcadfb4f2c?q=80&w=240",
    )

    private val all = listOf(
        johnMobbin,
        miaRuns,
        nikoAfterDark,
        coachCam,
        lenaJpg,
        jamieOutside,
        alexAway,
        sunnyWithSam,
        milesLift,
        softLaunch,
        taylorPosted,
        cityEm,
        kira5k,
    )

    fun findWithId(id: ProfileId): UserProfile = all.firstOrNull { it.id == id } ?: error("No profile found with id $id")

    fun searchResults(): List<UserProfile> = all.take(5)
}

object Posts {
    private val homeFeed = listOf(
        Post(
            id = "leg-day-mirror",
            authorId = UserProfiles.miaRuns.id,
            timestamp = "7m",
            body = "Leg day survived. Barely. Posting this before the stairs start collecting rent.",
            replyCount = 2,
            likeCount = 42,
            media = listOf(
                PostMedia("https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?q=80&w=1200"),
                PostMedia("https://images.unsplash.com/photo-1605296867304-46d5465a13f1?q=80&w=1200"),
                PostMedia("https://images.unsplash.com/photo-1517836357463-d25dfeac3438?q=80&w=1200"),
            ),
            portraitMedia = true,
        ),
        Post(
            id = "group-chat-dinner",
            authorId = UserProfiles.nikoAfterDark.id,
            timestamp = "5m",
            body = "Group chat said \"quick dinner\" and somehow we are on round three.",
            replyCount = 6,
            likeCount = 118,
            media = listOf(
                PostMedia("https://images.unsplash.com/photo-1529156069898-49953e39b3ac?q=80&w=1200"),
                PostMedia("https://images.unsplash.com/photo-1492684223066-81342ee5ff30?q=80&w=1200"),
            ),
        ),
        Post(
            id = "preworkout-truth",
            authorId = UserProfiles.coachCam.id,
            timestamp = "1h",
            body = "The hardest part of training is convincing yourself the walk to the gym counts as the warmup, not the workout.",
            replyCount = 9,
            likeCount = 86,
        ),
        Post(
            id = "elevator-selfie",
            authorId = UserProfiles.lenaJpg.id,
            timestamp = "2h",
            body = "Outfit made it out of the apartment so it deserves documentation.",
            replyCount = 12,
            likeCount = 203,
            media = listOf(
                PostMedia("https://images.unsplash.com/photo-1524504388940-b1c1722653e1?q=80&w=1200"),
                PostMedia("https://images.unsplash.com/photo-1517841905240-472988babdf9?q=80&w=1200"),
            ),
            portraitMedia = true,
        ),
        Post(
            id = "saturday-market",
            authorId = UserProfiles.jamieOutside.id,
            timestamp = "3h",
            body = "Farmers market, iced coffee, no real plan. Strong start to the weekend.",
            replyCount = 4,
            likeCount = 71,
            media = listOf(
                PostMedia("https://images.unsplash.com/photo-1488459716781-31db52582fe9?q=80&w=1200"),
                PostMedia("https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?q=80&w=1200"),
            ),
        ),
        Post(
            id = "airport-mode",
            authorId = UserProfiles.alexAway.id,
            timestamp = "4h",
            body = "Airport fit, 19 tabs open, and somehow still convinced I packed the wrong charger.",
            replyCount = 12,
            likeCount = 104,
        ),
        Post(
            id = "beach-roll",
            authorId = UserProfiles.sunnyWithSam.id,
            timestamp = "5h",
            body = "Camera roll from today is 90% sky, water, and people pretending they are not sunburned.",
            replyCount = 6,
            likeCount = 167,
            media = listOf(
                PostMedia("https://images.unsplash.com/photo-1507525428034-b723cf961d3e?q=80&w=1200"),
                PostMedia("https://images.unsplash.com/photo-1519046904884-53103b34b206?q=80&w=1200"),
                PostMedia("https://images.unsplash.com/photo-1520454974749-611b7248ffdb?q=80&w=1200"),
            ),
        ),
        Post(
            id = "new-pr",
            authorId = UserProfiles.milesLift.id,
            timestamp = "6h",
            body = "Hit a new PR and immediately forgot every normal way to stand for the photo.",
            replyCount = 3,
            likeCount = 129,
            media = listOf(PostMedia("https://images.unsplash.com/photo-1581009146145-b5ef050c2e1e?q=80&w=1200")),
            portraitMedia = true,
        ),
        Post(
            id = "late-latte",
            authorId = UserProfiles.softLaunch.id,
            timestamp = "8h",
            body = "Said I was going to work from the cafe. Mostly reviewed the playlist and called it admin.",
            replyCount = 15,
            likeCount = 138,
        ),
        Post(
            id = "birthday-dump",
            authorId = UserProfiles.taylorPosted.id,
            timestamp = "10h",
            body = "Birthday photo dump because posting them one at a time would be a public service failure.",
            replyCount = 8,
            likeCount = 92,
            media = listOf(
                PostMedia("https://images.unsplash.com/photo-1527529482837-4698179dc6ce?q=80&w=1200"),
                PostMedia("https://images.unsplash.com/photo-1530103862676-de8c9debad1d?q=80&w=1200"),
                PostMedia("https://images.unsplash.com/photo-1519671482749-fd09be7ccebf?q=80&w=1200"),
            ),
        ),
        Post(
            id = "night-walk",
            authorId = UserProfiles.cityEm.id,
            timestamp = "12h",
            body = "Took the long way home and the city decided to be dramatic about it.",
            replyCount = 2,
            likeCount = 77,
            media = listOf(
                PostMedia("https://images.unsplash.com/photo-1519501025264-65ba15a82390?q=80&w=1200"),
                PostMedia("https://images.unsplash.com/photo-1494526585095-c41746248156?q=80&w=1200"),
            ),
        ),
        Post(
            id = "post-run",
            authorId = UserProfiles.kira5k.id,
            timestamp = "14h",
            body = "Ran a 5K, rewarded myself with fries, achieved balance.",
            replyCount = 11,
            likeCount = 76,
        ),
    )

    private val profilePosts = mapOf(
        UserProfiles.johnMobbin.id to listOf(
            Post("coffee-follow", UserProfiles.johnMobbin.id, "4h", "Found the kind of cafe that makes you pretend you are about to write a novel.", 2, 11),
            Post(
                id = "morocco-dream",
                authorId = UserProfiles.johnMobbin.id,
                timestamp = "4h",
                body = "Always a dream to see the Medina in Morocco!",
                replyCount = 9,
                likeCount = 42,
                quoteAuthor = "earthpix",
                quoteBody = "What is one place you're absolutely traveling to by next year?",
                quoteReplyCount = 237,
            ),
            Post("late-train", UserProfiles.johnMobbin.id, "7h", "The train was late but the sunset did put in a formal apology.", 4, 28),
            Post("airport-window", UserProfiles.johnMobbin.id, "1d", "Window seat, tiny pretzels, and pretending turbulence is character development.", 5, 51),
        ),
        UserProfiles.miaRuns.id to listOf(
            Post("mia-leg-day", UserProfiles.miaRuns.id, "7m", "Leg day survived. Barely. Posting this before the stairs start collecting rent.", 2, 42),
            Post("mia-run-club", UserProfiles.miaRuns.id, "1h", "Run club pace was suspiciously close to sprint club.", 6, 33),
            Post("mia-smoothie", UserProfiles.miaRuns.id, "5h", "Smoothie looked healthy, tasted like lawn, still drank it.", 8, 91),
        ),
        UserProfiles.nikoAfterDark.id to listOf(
            Post("niko-dinner", UserProfiles.nikoAfterDark.id, "5m", "Group chat said quick dinner and somehow we are on round three.", 6, 118),
            Post("niko-rooftop", UserProfiles.nikoAfterDark.id, "3h", "Rooftop lighting is doing free brand strategy for everyone.", 9, 77),
            Post("niko-uber", UserProfiles.nikoAfterDark.id, "9h", "The ride home playlist deserves its own post.", 5, 48),
        ),
        UserProfiles.coachCam.id to listOf(
            Post("cam-warmup", UserProfiles.coachCam.id, "1h", "The walk to the gym counts as the warmup emotionally, not physiologically.", 9, 86),
            Post("cam-form-check", UserProfiles.coachCam.id, "4h", "Film your sets. Your memory is a liar with confidence.", 11, 84),
            Post("cam-rest-day", UserProfiles.coachCam.id, "1d", "Rest day means walking to get coffee and calling it mobility.", 6, 63),
        ),
        UserProfiles.lenaJpg.id to listOf(
            Post("lena-elevator", UserProfiles.lenaJpg.id, "2h", "Outfit made it out of the apartment so it deserves documentation.", 9, 83),
            Post("lena-jacket", UserProfiles.lenaJpg.id, "5h", "Bought the jacket for one dinner and immediately made it my personality.", 4, 36),
            Post("lena-gallery", UserProfiles.lenaJpg.id, "1d", "Museum mirror pics are culture. I do not make the rules.", 7, 58),
        ),
    )

    private val profileReplies = mapOf(
        UserProfiles.johnMobbin.id to listOf(
            Post("reply-gym", UserProfiles.johnMobbin.id, "2h", "That post-workout walk home always feels like a side quest.", 1, 14),
            Post(
                id = "reply-travel",
                authorId = UserProfiles.johnMobbin.id,
                timestamp = "6h",
                body = "Adding this to the travel list. The light in that courtyard looks unreal.",
                replyCount = 3,
                likeCount = 22,
                quoteAuthor = "jane_mobbin",
                quoteBody = "The quietest side street in Palermo still had the best espresso I had all year.",
                quoteReplyCount = 19,
            ),
            Post("reply-brunch", UserProfiles.johnMobbin.id, "1d", "Brunch only counts if someone orders one thing for the table and everyone steals it.", 2, 31),
        ),
        UserProfiles.miaRuns.id to listOf(
            Post("mia-reply-pr", UserProfiles.miaRuns.id, "2h", "New PR photos have a 70% chance of awkward thumbs up.", 1, 12),
            Post("mia-reply-stretch", UserProfiles.miaRuns.id, "6h", "Stretching for five minutes counts if I complain the whole time.", 3, 26),
        ),
        UserProfiles.nikoAfterDark.id to listOf(
            Post("niko-reply-table", UserProfiles.nikoAfterDark.id, "1h", "Ordering fries for the table is community leadership.", 4, 19),
            Post("niko-reply-photo", UserProfiles.nikoAfterDark.id, "7h", "The blurry photo is always the one with the best energy.", 2, 14),
        ),
        UserProfiles.coachCam.id to listOf(
            Post("cam-reply-strap", UserProfiles.coachCam.id, "3h", "Use straps when grip is the limiter, not pride.", 2, 31),
            Post("cam-reply-sleep", UserProfiles.coachCam.id, "8h", "Sleep is the supplement everyone skips.", 1, 17),
        ),
        UserProfiles.lenaJpg.id to listOf(
            Post("lena-reply-fit", UserProfiles.lenaJpg.id, "4h", "The accessories are doing all the work and I respect it.", 5, 44),
            Post("lena-reply-photo", UserProfiles.lenaJpg.id, "11h", "Take the photo anyway. You can overthink it later.", 2, 29),
        ),
    )

    fun homeFeed(): List<Post> = homeFeed

    fun postsByProfileId(profileId: ProfileId): List<Post> = profilePosts[profileId].orEmpty()

    fun repliesByProfileId(profileId: ProfileId): List<Post> = profileReplies[profileId].orEmpty()
}

object ActivityEvents {
    private val all = listOf(
        ActivityEvent(
            id = "john-mention",
            authorId = UserProfiles.johnMobbin.id,
            timestamp = "4h",
            type = ActivityEventType.Mention,
            body = "Here's a thread you should follow if you love botany @jane_mobbin",
        ),
        ActivityEvent(
            id = "john-reply",
            authorId = UserProfiles.johnMobbin.id,
            timestamp = "4h",
            type = ActivityEventType.Comment,
            body = "Count me in!",
        ),
        ActivityEvent(
            id = "plantdads-follow",
            authorId = UserProfiles.coachCam.id,
            timestamp = "5h",
            type = ActivityEventType.Follow,
        ),
        ActivityEvent(
            id = "plantdads-like",
            authorId = UserProfiles.lenaJpg.id,
            timestamp = "5h",
            type = ActivityEventType.Like,
            context = "Definitely broken! 🧵 👀 🌱",
        ),
        ActivityEvent(
            id = "berryjungle-like",
            authorId = UserProfiles.miaRuns.id,
            timestamp = "5h",
            type = ActivityEventType.Like,
            context = "🌱 👀 🧵",
        ),
    )

    fun recent(): List<ActivityEvent> = all
}

data class ActivityEvent(
    val id: String,
    val timestamp: String,
    val authorId: ProfileId,
    val type: ActivityEventType,
    val body: String? = null,
    val context: String? = null,
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
