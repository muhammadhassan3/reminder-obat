package com.muhammhassan.reminderobat.core.service.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.muhammhassan.reminderobat.core.service.receiver.DrugsReminder
import com.muhammhassan.reminderobat.core.service.receiver.StartReminder
import com.muhammhassan.reminderobat.core.service.receiver.StopReminder
import com.muhammhassan.reminderobat.core.utils.Constant
import timber.log.Timber
import java.util.*

class AndroidAlarmScheduler(private val context: Context) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun scheduleReminder(item: AlarmModel) {
        val calendar = Calendar.getInstance()
        val time = item.time.split(":")
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, time[0].toInt())
        calendar.set(Calendar.MINUTE, time[1].toInt())
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)


        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar[Calendar.DAY_OF_MONTH] = calendar[Calendar.DAY_OF_MONTH] + 1
        }

        val intent = Intent(context, DrugsReminder::class.java)
        intent.putExtra(Constant.id, item.scheduleId)
        intent.putExtra(Constant.title, item.getTitle())
        intent.putExtra(Constant.message, item.getMessage())
        intent.putExtra(Constant.days, item.days)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.scheduleId,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent
        )

        Timber.i("Schedule setted at ${calendar.time}")
    }

    override fun cancelSchedule(scheduleId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DrugsReminder::class.java)

        val pendingIntent =
            PendingIntent.getBroadcast(context, scheduleId, intent, PendingIntent.FLAG_IMMUTABLE)

        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
        Timber.i("Schedule canceled")
    }

    override fun scheduleStartReminder(drugsId: Int, startDate: Date) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, StartReminder::class.java).also {
            it.putExtra(Constant.id, drugsId)
        }
        val pendingIntent =
            PendingIntent.getBroadcast(context, drugsId, intent, PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance()
        calendar.time = startDate
        calendar.set(Calendar.HOUR_OF_DAY,0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND,0)
        alarmManager.setExact(AlarmManager.RTC, calendar.timeInMillis, pendingIntent)
        Timber.i("Schedule Start Date")
    }

    override fun rescheduleAlarm(id: Int) {
        val calendar  = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, 5)

        val intent = Intent(context, DrugsReminder::class.java)
        intent.putExtra(Constant.id, id)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
        )

        Timber.d("Alarm rescheduled")
    }

    override fun cancelScheduleStart(drugsId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, StartReminder::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, drugsId, intent, PendingIntent.FLAG_IMMUTABLE)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Timber.i("Schedule Start Canceled")
    }

    override fun scheduleClearReminder(drugsId: Int, endDate: Date) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, StopReminder::class.java).also {
            it.putExtra(Constant.id, drugsId)
        }
        val pendingIntent =
            PendingIntent.getBroadcast(context, drugsId, intent, PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance()
        calendar.time = endDate
        calendar.set(Calendar.HOUR_OF_DAY,0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND,0)
        calendar.add(Calendar.DAY_OF_MONTH, 1)//set Timer for the next day
        alarmManager.setExact(AlarmManager.RTC, calendar.timeInMillis, pendingIntent)
        Timber.i("Schedule End Date")
    }

    override fun cancelScheduleClear(drugsId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, StopReminder::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, drugsId, intent, PendingIntent.FLAG_IMMUTABLE)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Timber.i("Schedule End Canceled")
    }
}