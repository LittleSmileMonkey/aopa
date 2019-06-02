package com.sleep.retrofit_analysis

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_retrofit_analysis.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitAnalysisActivity : AppCompatActivity() {

    private val TAG = "AppCompatActivity"

    private lateinit var retrofitApi: RetrofitApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_analysis)
        retrofitApi = initRetrofitApi()
        stv_start_fetch.setOnClickListener {
            retrofitApi.getUser("1").enqueue(object : Callback<Any> {
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.d(TAG, "fail invoke")
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    Log.d(TAG, "body = ${response.body()}")
                }
            })
        }
    }

    private fun initRetrofitApi(): RetrofitApi {
        val client = initOkHttp()
        val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.0.104:9000")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(RetrofitApi::class.java)
    }

    private fun initOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLogging.httpLoggingInterceptor)
                .build()
    }
}
