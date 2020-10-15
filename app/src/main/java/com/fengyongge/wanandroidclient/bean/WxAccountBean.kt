package com.fengyongge.wanandroidclient.bean

class WxAccountBean : ArrayList<WxAccountBeanItem>()

data class WxAccountBeanItem(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    var choose: Boolean
)