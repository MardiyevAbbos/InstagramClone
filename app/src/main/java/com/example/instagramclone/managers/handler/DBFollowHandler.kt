package com.example.instagramclone.managers.handler

import java.lang.Exception

interface DBFollowHandler {
    fun onSuccess(isDone: Boolean)
    fun onError(e: Exception)
}