package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.api.ExperienceService
import com.frontend.oportunia.data.remote.dto.ExperienceDto
import retrofit2.Response
import javax.inject.Inject

class ExperienceDataSource @Inject constructor (
    private val experienceService: ExperienceService
) {
    suspend fun getAllExperiences(): Result<List<ExperienceDto>> = safeApiCall {
        experienceService.getAllExperiences()
    }

    suspend fun getExperienceById(id: Long): Result<ExperienceDto> = safeApiCall {
        experienceService.getExperienceById(id)
    }

    suspend fun getExperiencesByStudentId(studentId: Long): Result<List<ExperienceDto>> = safeApiCall {
        experienceService.getExperiencesByStudentId(studentId)
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> = try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Response body was null"))
        } else {
            val errorBody = response.errorBody()?.string()
            Result.failure(Exception("API error ${response.code()}: $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}