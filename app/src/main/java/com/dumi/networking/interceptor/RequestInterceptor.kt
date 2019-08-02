package com.dumi.networking.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

const val OK_HTTP_CLIENT_TIMEOUT: Long = 3000

const val HEADER_KEY_ACCEPT: String = "Accept"
const val HEADER_VALUE_ACCEPT_JSON: String = "application/json"
const val HEADER_KEY_ACCEPT_CHARSET: String = "Accept-Charset"
const val HEADER_VALUE_ACCEPT_CHARSET_UTF8: String = "utf-8"

const val JSON_RESPONSE_CURRENCIES_RESULTS_KEY = "results"

object RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val newRequest: Request = originalRequest.newBuilder()
            .addHeader(HEADER_KEY_ACCEPT, HEADER_VALUE_ACCEPT_JSON)
            .addHeader(HEADER_KEY_ACCEPT_CHARSET, HEADER_VALUE_ACCEPT_CHARSET_UTF8)
            .method(originalRequest.method(), null)
            .build()

        return chain.proceed(newRequest)
    }
}