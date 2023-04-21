package com.muhammhassan.reminderobat.core.service.receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.muhammhassan.reminderobat.core.R
import com.muhammhassan.reminderobat.core.service.AlarmService
import com.muhammhassan.reminderobat.core.utils.Constant
import timber.log.Timber
import java.util.*

class DrugsReminder : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getIntExtra(Constant.id, 0) ?: 0
        val title = intent?.getStringExtra(Constant.title) ?: ""
        val message = intent?.getStringExtra(Constant.message) ?: ""
        val days = intent?.getStringExtra(Constant.days) ?: ""

        context?.let { ctx ->
            if (isToday(days)) {
                val intentService = Intent(ctx, AlarmService::class.java)
                intentService.putExtra(Constant.id, id)
                intentService.putExtra(Constant.title, title)
                intentService.putExtra(Constant.message, message)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ctx.startForegroundService(intentService)
                }else{
                    ctx.startService(intentService)
                }
            }
        }
        Timber.i("Alarm $id Invoked")
    }

    private fun showNotification(context: Context, title: String, message: String, id: Int) {
        val notifManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val uri = Uri.parse("reminderobat://alarm?id=$id")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, Constant.channelId).setContentIntent(pendingIntent)
            .setAutoCancel(true).setContentTitle(title).setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_MAX).setSound(alarmSound)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setSmallIcon(R.drawable.app_icon_monochrome)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constant.channelId, "Reminder Notification Channel", NotificationManager.IMPORTANCE_HIGH
            ).also { channel ->
                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000)
            }

            builder.setChannelId(Constant.channelId)

            notifManager.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notification.flags = Notification.FLAG_AUTO_CANCEL or Notification.FLAG_ONGOING_EVENT
        notifManager.notify(0, notification)
    }

    private fun isToday(days: String?): Boolean {
        val calendar = Calendar.getInstance()
        val day = calendar[Calendar.DAY_OF_WEEK]

        return days?.contains(day.toString()) ?: false
    }
}