package com.fengyongge.wanandroidclient.bean.openeye

import com.chad.library.adapter.base.entity.MultiItemEntity

data class OpenEyeDailyBean(
    val adExist: Boolean,
    val count: Int,
    val itemList: List<Item>,
    val nextPageUrl: String,
    val total: Int
)

data class Item(
    val adIndex: Int,
    val `data`: OpenEyeData,
    val id: Int,
    val tag: Any,
    val type: String, override var itemType: Int
): MultiItemEntity{
    companion object {
        const val TYPE_ONE = 1
        const val TYPE_TWO = 2
        const val TYPE_THREE = 3
    }
}



data class OpenEyeData(
    val actionUrl: Any,
    val adTrack: Any,
    val content: Content,
    val dataType: String,
    val follow: Any,
    val header: Header,
    val id: Int,
    val subTitle: Any,
    val text: String,
    val type: String
)

data class Content(
    val adIndex: Int,
    val `data`: OpenEyeDataX,
    val id: Int,
    val tag: Any,
    val type: String
)

data class Header(
    val actionUrl: String,
    val cover: Any,
    val description: String,
    val followType: String,
    val font: Any,
    val icon: String,
    val iconType: String,
    val id: Int,
    val issuerName: String,
    val label: Any,
    val labelList: Any,
    val rightText: Any,
    val showHateVideo: Boolean,
    val subTitle: Any,
    val subTitleFont: Any,
    val tagId: Int,
    val tagName: Any,
    val textAlign: String,
    val time: Long,
    val title: String,
    val topShow: Boolean
)

data class OpenEyeDataX(
    val ad: Boolean,
    val adTrack: List<Any>,
    val addWatermark: Boolean,
    val area: String,
    val author: Author,
    val brandWebsiteInfo: Any,
    val campaign: Any,
    val category: String,
    val checkStatus: String,
    val city: String,
    val collected: Boolean,
    val consumption: Consumption,
    val cover: Cover,
    val createTime: Long,
    val dataType: String,
    val date: Long,
    val description: String,
    val descriptionEditor: String,
    val descriptionPgc: Any,
    val duration: Int,
    val favoriteAdTrack: Any,
    val height: Int,
    val id: Int,
    val idx: Int,
    val ifLimitVideo: Boolean,
    val ifMock: Boolean,
    val label: Any,
    val labelList: List<Any>,
    val lastViewTime: Any,
//    val latitude: Int,
    val library: String,
//    val longitude: Long,
    val owner: Owner,
    val playInfo: List<PlayInfo>,
    val playUrl: String,
    val playUrlWatermark: String,
    val played: Boolean,
    val playlists: Any,
    val privateMessageActionUrl: Any,
    val promotion: Any,
    val provider: Provider,
    val reallyCollected: Boolean,
    val recallSource: Any,
    val recall_source: Any,
    val recentOnceReply: RecentOnceReply,
    val releaseTime: Long,
    val remark: Any,
    val resourceType: String,
    val searchWeight: Int,
    val selectedTime: Long,
    val shareAdTrack: Any,
    val slogan: Any,
    val src: Any,
    val status: Any,
    val subtitles: List<Any>,
    val tags: List<OpenEyeTag>,
    val thumbPlayUrl: Any,
    val title: String,
    val titlePgc: Any,
    val transId: Any,
    val type: String,
    val uid: Int,
    val updateTime: Long,
    val url: String,
    val urls: List<String>,
    val urlsWithWatermark: List<String>,
    val validateResult: String,
    val validateStatus: String,
    val validateTaskId: String,
    val videoPosterBean: Any,
    val waterMarks: Any,
    val webAdTrack: Any,
    val webUrl: WebUrl,
    val width: Int
)

data class Author(
    val adTrack: Any,
    val approvedNotReadyVideoCount: Int,
    val description: String,
    val expert: Boolean,
    val follow: Follow,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val link: String,
    val name: String,
    val recSort: Int,
    val shield: Shield,
    val videoNum: Int
)

data class Consumption(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class Cover(
    val blurred: Any,
    val detail: String,
    val feed: String,
    val homepage: Any,
    val sharing: Any
)

data class Owner(
    val actionUrl: String,
    val area: Any,
    val avatar: String,
    val birthday: String,
    val city: String,
    val country: String,
    val cover: String,
    val description: String,
    val expert: Boolean,
    val followed: Boolean,
    val gender: String,
    val ifPgc: Boolean,
    val job: Any,
    val library: String,
    val limitVideoOpen: Boolean,
    val nickname: String,
    val registDate: Long,
    val releaseDate: Long,
    val uid: Int,
    val university: Any,
    val userType: String
)

data class PlayInfo(
    val height: Int,
    val name: String,
    val type: String,
    val url: String,
    val urlList: List<Url>,
    val width: Int
)

data class Provider(
    val alias: String,
    val icon: String,
    val name: String
)

data class RecentOnceReply(
    val actionUrl: String,
    val contentType: Any,
    val dataType: String,
    val message: String,
    val nickname: String
)

data class OpenEyeTag(
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

data class WebUrl(
    val forWeibo: String,
    val raw: String
)

data class Follow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)

data class Shield(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)

data class Url(
    val name: String,
    val size: Int,
    val url: String
)