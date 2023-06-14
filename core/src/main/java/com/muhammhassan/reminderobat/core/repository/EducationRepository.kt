package com.muhammhassan.reminderobat.core.repository

import com.muhammhassan.reminderobat.core.api.ApiResponse
import com.muhammhassan.reminderobat.core.api.response.Article
import kotlinx.coroutines.flow.Flow

interface EducationRepository {
    fun getEducation(): Flow<ApiResponse<List<Article>>>
}