package com.example.instagramclone.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.instagramclone.R
import com.example.instagramclone.activity.MainActivity
import com.example.instagramclone.utils.Logger
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random

private const val CHANNEL_ID = "my_channel"
class FCMService : FirebaseMessagingService() {
    val TAG = FCMService::class.java.simpleName

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Logger.i(TAG, "Refreshed token :: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String){

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val requestCode = (0..10).random()
        val pendingIntent = PendingIntent.getActivity(this, requestCode,
            openSandNotePage(message), FLAG_IMMUTABLE
        )

        val title = message.data["title"]
        val body = message.data["body"]
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notification)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.success_png))
            .setStyle(
                if (message.data["image"].isNullOrEmpty()){
                    NotificationCompat.BigTextStyle().bigText(message.data["body"]).setBigContentTitle(message.data["title"])
                }else{
                    NotificationCompat.BigPictureStyle().setSummaryText(message.data["body"]).bigPicture(getBitmapFromUrl(message.data["image"]))
                }
            )
            .setAutoCancel(true)
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createNotificationChannel(manager)
        val notificationId = Random.nextInt()
        manager.notify(notificationId,notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(manager: NotificationManager) {
        val channelName = "channelName"
        val channel = NotificationChannel(CHANNEL_ID,channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "My channel description"
            enableLights(true)
            lightColor = Color.BLUE
        }
        manager.createNotificationChannel(channel)
    }

    private fun getBitmapFromUrl(imageUrl: String?): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            null
        }
    }

    private fun openSandNotePage(message: RemoteMessage): Intent{
        val type = message.data["type"]

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra("type", type)

        return intent
    }

}