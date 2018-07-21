package com.geerydev.tyler.geerydev.model

import java.util.Date

data class Project (
    val _id: String,
    val type: String,
    val image: String,
    val link: String,
    val detail: String,
    val created: Date
)