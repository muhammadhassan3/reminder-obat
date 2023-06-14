package com.muhammhassan.reminderobat.core.repository

import com.muhammhassan.reminderobat.core.api.ApiResponse
import com.muhammhassan.reminderobat.core.api.response.Article
import com.muhammhassan.reminderobat.core.datasource.RemoteDatasource
import kotlinx.coroutines.flow.Flow

class EducationRepositoryImpl(private val remote: RemoteDatasource): EducationRepository {
    override fun getEducation(): Flow<ApiResponse<List<Article>>> {
        return remote.getData()
    }
}