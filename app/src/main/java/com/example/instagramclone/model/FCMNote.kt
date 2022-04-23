package com.example.instagramclone.model

data class FCMNote(
    val data: Data,
    val registration_ids: ArrayList<String>
)

data class Data(
    val title: String,
    val body: String,
    val image: String,
    val type: String
)