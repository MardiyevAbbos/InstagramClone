package com.example.instagramclone.managers.handler

import com.example.instagramclone.model.User
import java.lang.Exception

interface DBUserHandler {
    fun onSuccess(user: User? = null)
    fun onError(e: Exception)
}