package com.geerydev.tyler.geerydev.network

import com.geerydev.tyler.geerydev.model.Post
import com.geerydev.tyler.geerydev.model.Project
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeeryDevService {

    @GET("blogs")
    fun fetchPosts(@Query("sort") sort: String,
                      @Query("exists") exists: String,
                      @Query("page") page: Int,
                      @Query("per_page") per_page: Int):
            Observable<List<Post>>

    @GET("blogs/{id}")
    fun fetchPost(@Path("id") id: String):
            Single<Post>

    @GET("projects")
    fun fetchProjects(): Observable<List<Project>>

    companion object {
        fun create(): GeeryDevService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://www.geerydev.com/api/")
                    .build()

            return retrofit.create(GeeryDevService::class.java)
        }
    }
}