package com.frontend.oportunia.data.datasource


import com.frontend.oportunia.data.remote.api.IAAnalysisService
import com.frontend.oportunia.data.remote.dto.IAAnalysisDto
import retrofit2.Response
import javax.inject.Inject

class IAAnalysisDataSource @Inject constructor(
    private val iAAnalysisService: IAAnalysisService
) {

    suspend fun getIAAnalyses(): Result<List<IAAnalysisDto>> = safeApiCall {
        iAAnalysisService.getAllIAAnalyses()
    }

    suspend fun getIAAnalysisById(id: Long): Result<IAAnalysisDto> = safeApiCall {
        iAAnalysisService.getIAAnalysisById(id)
    }

    suspend fun insertIAAnalysis(iaAnalysisDto: IAAnalysisDto): Response<IAAnalysisDto> {
        return iAAnalysisService.createIAAnalysis(iaAnalysisDto)
    }

    suspend fun deleteIAAnalysis(id: Long): Response<Unit> {
        return iAAnalysisService.deleteIAAnalysis(id)
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
