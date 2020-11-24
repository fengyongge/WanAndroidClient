package com.fengyongge.wanandroidclient.api

import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.wanandroidclient.bean.*
import com.fengyongge.wanandroidclient.bean.openeye.OpenEyeDailyBean
import com.fengyongge.wanandroidclient.bean.openeye.OpenEyeRelateCommentBean
import com.fengyongge.wanandroidclient.bean.openeye.OpenEyeRelateVideoBean
import com.fengyongge.basecomponent.constant.Const.Companion.URL_OPEN_EYE
import io.reactivex.Observable
import retrofit2.http.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
interface WanandroidApi {

    /**
     * 首页banner
     */
    @GET("banner/json")
    fun bannerList(): Observable<BaseResponse<List<BannerBean>>>

    /**
     * 导航
     */
    @GET("navi/json")
    fun getNavigation(): Observable<BaseResponse<List<NavigationBean>>>

    /**
     * 获取体系分类
     */
    @GET("tree/json")
    fun getSystemTree(): Observable<BaseResponse<List<SystemCategoryBean>>>

    /**
     * 获取体系下的详情
     */
    @GET("article/list/{pageNum}/json")
    fun getSystemProject(@Path("pageNum") pageNum: Int,@Query("cid")cid: Int): Observable<BaseResponse<SystemArticleBean>>

    /**
     * 项目分类
     */
    @GET("project/tree/json")
    fun getProjectType(): Observable<BaseResponse<List<ProjectTypeBeanItem>>>


    /**
     * 问答
     */
    @GET("wenda/list/{pageNum}/json")
    fun getQuestionAnswer(@Path("pageNum")pageNum: Int): Observable<BaseResponse<QuestionAnswerBean>>

    /**
     * 分类下面的项目
     */
    @GET("project/list/{pageNum}/json")
    fun getProjectByType(@Path("pageNum")pageNum: Int,@Query("cid")cid: String): Observable<BaseResponse<ProjectBean>>

    /**
     * 文章搜索
     */
    @GET("article/list/{pageNum}/json")
    fun getProjectSearch(@Path("pageNum") pageNum: Int,@Query("author")author: String): Observable<BaseResponse<ArticleBean>>


    /**
     * 首页热门置顶文章列表
     */
    @GET("article/top/json")
    fun stickArticle(): Observable<BaseResponse<List<DataX>>>


    /**
     * 首页热门文章列表
     */
    @GET("article/list/{pageNum}/json")
    fun articleList(@Path("pageNum") pageNum: Int): Observable<BaseResponse<ArticleBean>>

    /**
     * 首页热门项目列表
     */
    @GET("article/listproject/{pageNum}/json")
    fun projectList(@Path("pageNum") pageNum: Int): Observable<BaseResponse<ArticleBean>>

    /**
     * 获取广场数据
     */
    @GET("user_article/list/{pageNum}/json")
    fun getSquareProject(@Path("pageNum")pageNum: Int): Observable<BaseResponse<SquareBean>>


    /**
     * 获取热搜
     */
    @GET("hotkey/json")
    fun getHotKey(): Observable<BaseResponse<List<HotKeyBeanItem>>>


    /**
     * 文章搜索
     */
    @FormUrlEncoded
    @POST("article/query/{pageNum}/json")
    fun postSearchContent(@Path("pageNum")pageNum: Int, @Field("k")searchContent: String): Observable<BaseResponse<SearchContentBean>>

    /**
     * 公众号列表
     */
    @GET("wxarticle/chapters/json")
    fun getWxAccount(): Observable<BaseResponse<List<WxAccountBeanItem>>>

    /**
     * 查看某个公众号历史数据
     */
    @GET("wxarticle/list/{id}/{pageNum}/json")
    fun getWxHistoryList(@Path("id")id: String,@Path("pageNum")pageNum: Int): Observable<BaseResponse<WxHistoryBean>>

    /**
     * 在某个公众号中搜索历史文章
     */
    @GET("wxarticle/list/{id}/{pageNum}/json?")
    fun getSearchWxContent(@Path("id")id: Int,@Path("pageNum")pageNum: Int,@Query("k")content: String): Observable<BaseResponse<WxAccountSearchBean>>



    /**
     * 登出
     */
    @GET("user/logout/json")
    fun logout(): Observable<BaseResponse<String>>

    /**
     * 获取个人积分
     */
    @GET("lg/coin/userinfo/json")
    fun getAccount(): Observable<BaseResponse<UserInforBean>>


    /**
     * 收藏文章列表
     */
    @GET("lg/collect/list/{pageNum}/json")
    fun getCollectList(@Path("pageNum")pageNum: Int): Observable<BaseResponse<MyCollectBean>>

    /**
     * 我的收藏列表中取消收藏文章
     */
    @POST("lg/uncollect/{id}/json")
    fun postCancleMyCollect(@Path("id")id: String,@Query("originId") originId: Int): Observable<BaseResponse<String>>

    /**
     * 收藏文章
     */
    @POST("lg/collect/{id}/json")
    fun postCollect(@Path("id")id: Int): Observable<BaseResponse<String>>

    /**
     * 取消收藏文章
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun postCancleCollect(@Path("id")id: Int): Observable<BaseResponse<String>>


    /**
     * 分享文章或者项目
     */
    @FormUrlEncoded
    @POST("lg/user_article/add/json")
    fun postShare(@Field("title")title: String,@Field("link")link: String): Observable<BaseResponse<String>>

    /**
     * 获取我的分享列表
     */
    @GET("user/lg/private_articles/{pageNum}/json")
    fun getShareList(@Path("pageNum")pageNum: Int): Observable<BaseResponse<MyShareBean>>

    /**
     * 删除我的分享
     */
    @POST("lg/user_article/delete/{id}/json")
    fun postDeleteMyShare(@Path("id")pageNum: Int): Observable<BaseResponse<String>>



    /**
     * 开眼每日精选
     */
    @Headers(URL_OPEN_EYE)
    @GET("api/v5/index/tab/feed")
    fun getOpenEyeDaily(@Query("date")date: String): Observable<OpenEyeDailyBean>

    /**
     * 开眼相关推荐
     */
    @Headers(URL_OPEN_EYE)
    @GET("api/v4/video/related")
    fun getOeRelateVideo(@Query("id")id: String): Observable<OpenEyeRelateVideoBean>


    /**
     * 开眼相关评论
     */
    @Headers(URL_OPEN_EYE)
    @GET("api/v2/replies/video")
    fun getOeRelateComment(@Query("videoId")videoId: String): Observable<OpenEyeRelateCommentBean>
}