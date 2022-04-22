package com.example.instagramclone.network.service

import com.example.instagramclone.model.FCMNote
import com.example.instagramclone.model.FCMResp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NoteService {
    companion object {
        private const val SERVER_KEY =
            "AAAAbGrKGhk:APA91bERi18kkzFsrEPRhXLtpPBdI3NMYHNYjgKuuECDnVn4Lm0Bpy3cVR_J08DkCMgUnNqsybYw1TQBPZJzHwU6ksJ7OCO3ouTBp_cXwbq5yvTEBRvnjA7yDAvfn-DpTP0Xn72NxrNz"
    }

    @Headers("Authorization:key=$SERVER_KEY")
    @POST("/fcm/send")
    fun sendNote(@Body fcmNote: FCMNote): Call<FCMResp>
}