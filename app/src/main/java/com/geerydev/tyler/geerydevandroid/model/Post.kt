package com.geerydev.tyler.geerydevandroid.model

data class Post(
    val question: String,
    val askedBy: String,
    val created: String,
    val email: String,
    val response: String,
    val totalComments: Int
)