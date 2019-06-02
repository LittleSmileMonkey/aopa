package com.sleep.retrofit_analysis

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_retrofit_analysis.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class RetrofitAnalysisActivity : AppCompatActivity() {

    private lateinit var retrofitApi: RetrofitApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_analysis)
        retrofitApi = initRetrofitApi()
        stv_start_fetch.setOnClickListener {

        }
    }

    private fun initRetrofitApi(): RetrofitApi {
        val client = initOkHttp()
        val retrofit = Retrofit.Builder()
                .baseUrl("http://127.0.0.0")
                .client(client)
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
