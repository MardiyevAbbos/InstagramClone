package com.example.instagramclone.managers.handler

import com.example.instagramclone.model.Post
import com.example.instagramclone.model.User
import java.lang.Exception

interface DBPostHandler {
    fun onSuccess(post: Post)
    fun onError(e: Exception)
}