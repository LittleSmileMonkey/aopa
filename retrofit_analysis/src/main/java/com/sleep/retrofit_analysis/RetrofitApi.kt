package com.sleep.retrofit_analysis

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * author：xingkong on 2019-06-02
 * e-mail：xingkong@changjinglu.net
 *
 */
interface RetrofitApi {

    @GET
    fun getUser(@Query("userId") userId: String): Call<Any>
}