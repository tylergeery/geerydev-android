package com.geerydev.tyler.geerydev.model

import java.util.Date

data class Post(
        val id: String,
        val askedBy: String,
        val question: String,
        val totalComments: Int,
        val created: Date
)