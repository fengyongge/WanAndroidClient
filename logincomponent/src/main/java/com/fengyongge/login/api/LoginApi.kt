package com.fengyongge.login.api

import com.fengyongge.login.bean.LoginBean
import com.fengyongge.login.bean.RegisterBean
import com.fengyongge.rxhttp.bean.BaseResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
interface LoginApi {

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username")username: String, @Field("password")password: String): Observable<BaseResponse<LoginBean>>


    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("user/register")
    fun register(@Field("username")username: String, @Field("password")password: String,
                 @Field("repassword")repassword: String): Observable<BaseResponse<RegisterBean>>

}