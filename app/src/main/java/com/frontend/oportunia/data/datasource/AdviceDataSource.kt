package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.api.AdviceService
import com.frontend.oportunia.data.remote.dto.AbilityDto
import com.frontend.oportunia.data.remote.dto.AdviceDto
import retrofit2.Response
import javax.inject.Inject

class AdviceDataSource @Inject constructor (
    private val adviceService: AdviceService
) {
    suspend fun getAllAdvices(): Result<List<AdviceDto>> = safeApiCall {
        adviceService.getAllAdvices()
    }

    suspend fun getAdvicesById(id: Long): Result<AdviceDto> = safeApiCall {
        adviceService.getAdviceById(id)
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