package com.fengyongge.wanandroidclient.bean.openeye

import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
class OpenEyeDetailBean(override val itemType: Int) : MultiItemEntity, Parcelable {
    lateinit var video: Video
    lateinit var comment: Comment
    lateinit var commentDes: String
    companion object {
        const val TYPE_ONE = 1
        const val TYPE_TWO = 2
        const val TYPE_THREE = 3
    }
}


@Parcelize
 class Video(
    var id: Int,
    var description: String,
    var title: String,
    var category: String,
    var coverImage: String,
    var duration: Int,
    var playUrl: String
) : Parcelable

@Parcelize
 class Comment(
    var nickname: String,
    var avatar: String,
    var message: String,
    var createTime: Long
) : Parcelable
