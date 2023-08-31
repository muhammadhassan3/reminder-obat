package com.muhammhassan.reminderobat.core.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.app.NotificationCompat
import com.muhammhassan.reminderobat.core.R
import com.muhammhassan.reminderobat.core.utils.Constant
import timber.log.Timber
import java.io.IOException

class AlarmService: Service() {
    private val mediaPlayer by lazy {
        MediaPlayer().also {
            it.isLooping = true
        }
    }
    private val vibrator by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val manager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            manager.defaultVibrator
        } else {
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
    }
    private var ringtone: Uri? = null

    override fun onCreate() {
        super.onCreate()
        ringtone = RingtoneManager.getActualDefaultRingtoneUri(applicationContext, RingtoneManager.TYPE_ALARM)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val id = intent?.getIntExtra(Constant.id, 0) ?: 0
        val title = intent?.getStringExtra(Constant.title) ?: "Waktunya minum obat"
        val message = intent?.getStringExtra(Constant.message) ?: "Ayo minum obatmu supaya kondisimu cepat membaik"
        val uri = Uri.parse("reminderobat://alarm?id=$id")

        val alarmIntent = Intent(Intent.ACTION_VIEW, uri)
        val pendingIntent = PendingIntent.getActivity(this,0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        try{
            mediaPlayer.setDataSource(applicationContext, Uri.parse(ringtone.toString()))
            mediaPlayer.prepareAsync()
        }catch (e: IOException){
            Timber.e(e)
        }


        val notifManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(this, Constant.channelId).setContentIntent(pendingIntent)
            .setAutoCancel(true).setContentTitle(title).setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_MAX).setSound(null)
            .setVibrate(longArrayOf(100, 300, 100, 300))
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(pendingIntent, true)
            .setSmallIcon(R.drawable.app_icon_monochrome)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constant.channelId, "Service Channel", NotificationManager.IMPORTANCE_HIGH
            ).also { channel ->
                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(500, 500, 500, 500)
            }

            builder.setChannelId(Constant.channelId)

            notifManager.createNotificationChannel(channel)
        }

        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createWaveform(longArrayOf(500,500,500,500), 0)

            vibrator.vibrate(effect)
        } else {
            vibrator.vibrate(longArrayOf(500,500), 0)
        }

        startForeground(id, builder.build())

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        vibrator.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}