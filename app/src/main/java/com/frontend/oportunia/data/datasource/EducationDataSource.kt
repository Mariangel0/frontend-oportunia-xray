package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.api.EducationService
import com.frontend.oportunia.data.remote.api.ExperienceService
import com.frontend.oportunia.data.remote.dto.EducationDto
import com.frontend.oportunia.data.remote.dto.ExperienceDto
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class EducationDataSource @Inject constructor (
    private val educationService: EducationService
) {
    suspend fun getAllEducations(): Result<List<EducationDto>> = safeApiCall {
        educationService.getAllEducations()
    }

    suspend fun createEducation(educationDto: EducationDto): Response<EducationDto>  {
        return educationService.createEducation(educationDto)
    }

    suspend fun getEducationById(id: Long): Result<EducationDto> = safeApiCall {
        educationService.getEducationById(id)
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