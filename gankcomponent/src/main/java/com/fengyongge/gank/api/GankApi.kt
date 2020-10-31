package com.fengyongge.gank.api

import com.fengyongge.basecomponent.constant.Const
import com.fengyongge.gank.bean.GankGirlBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
interface GankApi {

    /**
     * 获取妹子数据
     */
    @Headers(Const.URL_GANK)
    @GET("api/v2/data/category/Girl/type/Girl/page/{pageNum}/count/{pageSize}")
    fun getGankGirl(@Path("pageNum")pageNum: Int, @Path("pageSize")pageSize: Int): Observable<GankGirlBean>


}