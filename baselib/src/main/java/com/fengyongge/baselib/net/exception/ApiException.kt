package com.fengyongge.baselib.net.exception

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class ApiException(val errorCode: String, errorMessage: String) : RuntimeException(errorMessage)