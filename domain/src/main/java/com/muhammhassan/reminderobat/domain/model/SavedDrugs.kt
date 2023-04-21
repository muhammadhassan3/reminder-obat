package com.muhammhassan.reminderobat.domain.model

import com.muhammhassan.reminderobat.core.service.model.AlarmModel

data class SavedDrugs(
    val drugsId: Long,
    val scheduleList: List<AlarmModel>
)