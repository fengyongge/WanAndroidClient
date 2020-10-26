package com.fengyongge.wanandroidclient.activity

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayerStandard
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.fengyongge.androidcommonutils.ktutils.DialogUtils
import com.fengyongge.androidcommonutils.ktutils.ToastUtils
import com.fengyongge.baselib.mvp.BaseMvpActivity

import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.bean.openeye.*
import com.fengyongge.wanandroidclient.bean.openeye.OpenEyeDetailBean.Companion.TYPE_ONE
import com.fengyongge.wanandroidclient.bean.openeye.OpenEyeDetailBean.Companion.TYPE_THREE
import com.fengyongge.wanandroidclient.bean.openeye.OpenEyeDetailBean.Companion.TYPE_TWO
import com.fengyongge.wanandroidclient.mvp.contract.OpenEyeContract
import com.fengyongge.wanandroidclient.mvp.presenterImpl.OpenEysPresenterImpl
import kotlinx.android.synthetic.main.activity_open_eye_detail.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class OpenEyeDetailActivity : BaseMvpActivity<OpenEysPresenterImpl>(), OpenEyeContract.View{
    var list = mutableListOf<OpenEyeDetailBean>()
    private lateinit var myAdapter: MyAdapter
    private lateinit var playUrl: String
    private lateinit var title: String
    private lateinit var des: String
    private lateinit var id: String
    private lateinit var coverImage: String
    private lateinit var tag: String
    lateinit var jzVideoPlayerStandard: JZVideoPlayerStandard

    override fun initLayout(): Int {
        return R.layout.activity_open_eye_detail
    }

    override fun initView() {
        jzVideoPlayerStandard = findViewById(R.id.videoplayer)
        getIntentParms()
        rvRelatedRideo.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        myAdapter = MyAdapter(list)
        rvRelatedRideo.adapter = myAdapter
        myAdapter.setHeaderView(getHeadView(), 0)
        myAdapter.setOnItemClickListener { adapter, view, position ->
            var item = adapter.data[position] as OpenEyeDetailBean
            if (item.itemType == TYPE_ONE) {
                var intent = Intent(this, OpenEyeDetailActivity::class.java)
                var openEyeCusBean = OpenEyeCusBean(
                    item.video.title,
                    item.video.description,
                    item.video.playUrl,
                    "" + item.video.id,
                    "" + item.video.coverImage,
                    item.video.category
                )
                intent.putExtra("openEyeCusBean", openEyeCusBean)
                startActivity(intent)
                finish()
            }
        }
    }


    private fun getIntentParms() {
        var openEyeCusBean = intent.getParcelableExtra<OpenEyeCusBean>("openEyeCusBean")
        playUrl = openEyeCusBean.playUrl
        title = openEyeCusBean.title
        des = openEyeCusBean.des
        id = openEyeCusBean.id
        coverImage = openEyeCusBean.coverImage
        tag = openEyeCusBean.tag
        loadData()
    }

    private fun loadData() {
        jzVideoPlayerStandard.setUp(playUrl, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, title)
        Glide.with(this).load(coverImage)
            .into(jzVideoPlayerStandard.thumbImageView)
        mPresenter?.getOeRelateVideo(id)
    }

    private fun getHeadView(): View {
        var view = LayoutInflater.from(this).inflate(R.layout.activity_open_eye_detail_header, null)
        view.findViewById<TextView>(R.id.oeDetailTitle).text = title
        view.findViewById<TextView>(R.id.oeDetailCategory).text = "#${tag}"
        view.findViewById<TextView>(R.id.oeDetailDes).text = des
        return view
    }

    override fun initData() {
    }

    override fun onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        JZVideoPlayer.releaseAllVideos()
    }


    class MyAdapter(data: MutableList<OpenEyeDetailBean>?) :
        BaseMultiItemQuickAdapter<OpenEyeDetailBean, BaseViewHolder>(data), LoadMoreModule {
        init {
            addItemType(TYPE_ONE, R.layout.item_activity_open_eye_detail_related_rideo)
            addItemType(TYPE_TWO, R.layout.item_activity_open_eye_detail_related_comment)
            addItemType(TYPE_THREE, R.layout.item_activity_open_eye_detail_related_comment_des)
        }

        override fun convert(holder: BaseViewHolder, item: OpenEyeDetailBean) {
            when (item?.itemType) {
                TYPE_ONE -> {
                    var ivOeDetailCoverImage = holder.getView<ImageView>(R.id.ivOeDetailCoverImage)
                    var tvOeDetailDuration = holder.getView<TextView>(R.id.tvOeDetailDuration)
                    var tvOeDetailTitle = holder.getView<TextView>(R.id.tvOeDetailTitle)
                    var tvOeDetailTag = holder.getView<TextView>(R.id.tvOeDetailTag)
                    Glide.with(context)
                        .load(item.video.coverImage)
                        .transform(CenterCrop(), RoundedCorners(10))
                        .into(ivOeDetailCoverImage)
                    tvOeDetailDuration.text = TimeUtils.convertMinAndSec(item.video.duration)
                    tvOeDetailTitle.text = item.video.title
                    tvOeDetailTag.text = " #${item.video.category}"
                }
                TYPE_TWO -> {
                    var ivOeDetailUserLogo = holder.getView<ImageView>(R.id.ivOeDetailUserLogo)
                    var tvOeDetailUserName = holder.getView<TextView>(R.id.tvOeDetailUserName)
                    var tvOeDetailUserComment = holder.getView<TextView>(R.id.tvOeDetailUserComment)
                    var tvOeDetailUserCommentTime =
                        holder.getView<TextView>(R.id.tvOeDetailUserCommentTime)
                    Glide.with(context).load(item.comment.avatar)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .error(R.drawable.default_openeye_user)
                        .placeholder(R.drawable.default_openeye_user)
                        .fallback(R.drawable.default_openeye_user)
                        .into(ivOeDetailUserLogo)
                    tvOeDetailUserName.text = item.comment.nickname
                    tvOeDetailUserComment.text = item.comment.message
                    tvOeDetailUserCommentTime.text =
                        TimeUtils.formatDateLongToString(item.comment.createTime, "HH:mm")
                }
                TYPE_THREE -> {
                    var tvOeDetailCommentDes = holder.getView<TextView>(R.id.tvOeDetailCommentDes)
                    tvOeDetailCommentDes.text = item.commentDes
                }
                else -> {

                }
            }
        }
    }

    override fun initPresenter(): OpenEysPresenterImpl {
        return OpenEysPresenterImpl()
    }

    override fun getOpenEyeDailyShow(data: OpenEyeDailyBean) {

    }

    override fun getOpenEyeDailyShowFail(data: ResponseException) {

    }

    override fun getOeRelateVideoShow(data: OpenEyeRelateVideoBean) {
        if (data.itemList.isNotEmpty()) {
            DialogUtils.dismissProgressMD()
            for (item in data.itemList) {
                if (item.type == "videoSmallCard") {
                    var videoData = item.data
                    var video = Video(
                        videoData.id,
                        videoData.description,
                        videoData.title,
                        videoData.category,
                        videoData.cover.feed,
                        videoData.duration,
                        videoData.playUrl
                    )
                    var openEyeDetailBean = OpenEyeDetailBean(TYPE_ONE)
                    openEyeDetailBean.video = video
                    list.add(openEyeDetailBean)
                }
            }
            DialogUtils.dismissProgressMD()
        } else {
            DialogUtils.dismissProgressMD()
        }
        mPresenter?.getOeRelateComment(id)
    }

    override fun getOeRelateCommentShow(data: OpenEyeRelateCommentBean) {
        if (data.itemList.isNotEmpty()) {
            var openEyeDetailBean = OpenEyeDetailBean(TYPE_THREE)
            openEyeDetailBean.commentDes = "最新评论"
            list.add(openEyeDetailBean)
            for (item in data.itemList) {
                if (item.data.dataType == "ReplyBeanForClient") {
                    var commentItem = item.data
                    var commentBean = Comment(
                        commentItem.user.nickname,
                        commentItem.user.avatar,
                        commentItem.message,
                        commentItem.createTime
                    )
                    var openEyeDetailBean = OpenEyeDetailBean(TYPE_TWO)
                    openEyeDetailBean.comment = commentBean
                    list.add(openEyeDetailBean)
                }
            }
            DialogUtils.dismissProgressMD()
        } else {
            DialogUtils.dismissProgressMD()
        }

        myAdapter.setList(list)
    }

    override fun onError(data: ResponseException) {
        ToastUtils.showToast(this, data.getErrorMessage())
        DialogUtils.dismissProgressMD()
    }

}