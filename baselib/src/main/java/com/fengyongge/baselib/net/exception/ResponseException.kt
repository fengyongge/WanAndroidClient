package com.fengyongge.baselib.net.exception

import kotlin.Exception

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class ResponseException : Exception {
    private var errorCode: String
    private var errorMessage: String?

    constructor(throwable: Throwable, errorCode: Int, errorMessage: String?) : super(throwable) {
        this.errorCode = errorCode.toString()
        this.errorMessage = errorMessage
    }

    constructor(throwable: Throwable, errorCode: String, errorMessage: String?) : super(throwable) {
        this.errorCode = errorCode
        this.errorMessage = errorMessage
    }


    fun getErrorMessage(): String? {
        return errorMessage
    }


    override fun toString(): String {
        return "[$errorCode, $errorMessage]"
    }
}