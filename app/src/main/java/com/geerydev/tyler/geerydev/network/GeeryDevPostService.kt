package com.geerydev.tyler.geerydev.network

import com.geerydev.tyler.geerydev.model.Post
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GeeryDevPostService {

    @GET("blogs")
    fun fetchPosts(@Query("sort") sort: String,
                      @Query("exists") exists: String,
                      @Query("page") page: Int,
                      @Query("per_page") per_page: Int):
            Observable<List<Post>>

    companion object {
        fun create(): GeeryDevPostService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://www.geerydev.com/api/")
                    .build()

            return retrofit.create(GeeryDevPostService::class.java)
        }
    }
}