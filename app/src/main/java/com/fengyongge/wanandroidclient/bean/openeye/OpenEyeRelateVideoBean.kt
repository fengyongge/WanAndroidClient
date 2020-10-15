package com.fengyongge.wanandroidclient.bean.openeye

data class OpenEyeRelateVideoBean(
    val adExist: Boolean,
    val count: Int,
    val itemList: List<VideoItem>,
    val nextPageUrl: Any,
    val total: Int
)

data class VideoItem(
    val adIndex: Int,
    val `data`: VideoData,
    val id: Int,
    val tag: Any,
    val type: String
)

data class VideoData(
    val actionUrl: String,
    val ad: Boolean,
    val adTrack: Any,
    val author: VideoAuthor,
    val brandWebsiteInfo: Any,
    val campaign: Any,
    val category: String,
    val collected: Boolean,
    val consumption: VideoConsumption,
    val cover: VideoCover,
    val dataType: String,
    val date: Long,
    val description: String,
    val descriptionEditor: String,
    val descriptionPgc: Any,
    val duration: Int,
    val favoriteAdTrack: Any,
    val follow: Any,
    val id: Int,
    val idx: Int,
    val ifLimitVideo: Boolean,
    val label: Any,
    val labelList: List<Any>,
    val lastViewTime: Any,
    val library: String,
    val playInfo: List<VideoPlayInfo>,
    val playUrl: String,
    val played: Boolean,
    val playlists: Any,
    val promotion: Any,
    val provider: VideoProvider,
    val reallyCollected: Boolean,
    val recallSource: Any,
    val recall_source: Any,
    val releaseTime: Long,
    val remark: Any,
    val resourceType: String,
    val searchWeight: Int,
    val shareAdTrack: Any,
    val slogan: Any,
    val src: Any,
    val subTitle: Any,
    val subtitles: List<Any>,
    val tags: List<Tag>,
    val text: String,
    val thumbPlayUrl: Any,
    val title: String,
    val titlePgc: Any,
    val type: String,
    val videoPosterBean: Any,
    val waterMarks: Any,
    val webAdTrack: Any,
    val webUrl: VideoWebUrl
)

data class VideoAuthor(
    val adTrack: Any,
    val approvedNotReadyVideoCount: Int,
    val description: String,
    val expert: Boolean,
    val follow: VideoFollow,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val link: String,
    val name: String,
    val recSort: Int,
    val shield: VideoShield,
    val videoNum: Int
)

data class VideoConsumption(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class VideoCover(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: String,
    val sharing: Any
)

data class VideoPlayInfo(
    val height: Int,
    val name: String,
    val type: String,
    val url: String,
    val urlList: List<VideoUrl>,
    val width: Int
)

data class VideoProvider(
    val alias: String,
    val icon: String,
    val name: String
)

data class Tag(
    val actionUrl: String,
    val adTrack: Any,
    val bgPicture: String,
    val childTagIdList: Any,
    val childTagList: Any,
    val communityIndex: Int,
    val desc: String,
    val haveReward: Boolean,
    val headerImage: String,
    val id: Int,
    val ifNewest: Boolean,
    val name: String,
    val newestEndTime: Any,
    val tagRecType: String
)

data class VideoWebUrl(
    val forWeibo: String,
    val raw: String
)

data class VideoFollow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)

data class VideoShield(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)

data class VideoUrl(
    val name: String,
    val size: Int,
    val url: String
)