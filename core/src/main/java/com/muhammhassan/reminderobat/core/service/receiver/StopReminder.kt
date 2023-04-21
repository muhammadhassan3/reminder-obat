package com.muhammhassan.reminderobat.core.service.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.muhammhassan.reminderobat.core.di.KoinHelper
import com.muhammhassan.reminderobat.core.service.model.AndroidAlarmScheduler
import com.muhammhassan.reminderobat.core.utils.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StopReminder : BroadcastReceiver() {
    private val helper by lazy { KoinHelper() }
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getIntExtra(Constant.id, 0) ?: 0
        context?.let { ctx ->
            val scheduleHelper = AndroidAlarmScheduler(ctx)
            CoroutineScope(Dispatchers.IO).launch {
                helper.getDrugsDetail(id).first {data ->
                    data.schedule.forEach {item ->
                        scheduleHelper.cancelSchedule(item.id?.toInt() ?: 0)
                    }
                    true
                }
            }
        }
    }
}