package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.api.StreakService
import com.frontend.oportunia.data.remote.dto.StreakDto
import retrofit2.Response
import javax.inject.Inject

class StreakDataSource @Inject constructor (
    private val streakService: StreakService
) {

    suspend fun getStreaks(): Result<List<StreakDto>> = safeApiCall {
        streakService.getAllStreaks()
    }

    suspend fun getStreakById(id: Long): Result<StreakDto> = safeApiCall {
        streakService.getStreakById(id)
    }

    suspend fun getStreakByStudentId(studentId: Long): Result<StreakDto> = safeApiCall {
        streakService.getStreakByStudentId(studentId)
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