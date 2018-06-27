package com.geerydev.tyler.geerydevandroid.network

import com.geerydev.tyler.geerydevandroid.model.Post
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * The interface which provides methods to get result of webservices
 */
interface PostApi {
    /**
     * Get the list of the pots from the API
     */
    @GET("/api/posts")
    fun getPosts(): Observable<List<Post>>
}