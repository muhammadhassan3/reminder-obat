package com.muhammhassan.reminderobat.core.service.alarm

import java.util.Date

interface AlarmScheduler {
    fun scheduleReminder(item: AlarmModel)
    fun cancelSchedule(scheduleId: Int)
    fun scheduleStartReminder(drugsId: Int, startDate: Date)

    fun rescheduleAlarm(id: Int)
    fun cancelScheduleStart(drugsId: Int)
    fun scheduleClearReminder(drugsId: Int, endDate: Date)
    fun cancelScheduleClear(drugsId: Int)
}