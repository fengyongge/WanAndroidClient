package com.fengyongge.baselib.net
/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
data class BaseResponse<T>(val errorMsg: String, val errorCode: String, var data: T)


