package com.frontend.oportunia.data.datasource

import android.util.Log
import com.frontend.oportunia.data.remote.api.CompanyReviewService
import com.frontend.oportunia.data.remote.dto.CompanyReviewDto
import jakarta.inject.Inject
import retrofit2.Response

class CompanyReviewDataSource @Inject constructor(
    private val companyReviewService: CompanyReviewService
) {

    suspend fun getAllReviews(): Result<List<CompanyReviewDto>> = safeApiCall {
        companyReviewService.getAllReviews()
    }

    suspend fun getReviewById(id: Long): Result<CompanyReviewDto> = safeApiCall {
        companyReviewService.getReviewById(id)
    }

    suspend fun getReviewsByCompanyId(companyId: Long): Result<List<CompanyReviewDto>> = safeApiCall {
        companyReviewService.getReviewsByCompanyId(companyId)
    }

    suspend fun createReview(review: CompanyReviewDto): Response<CompanyReviewDto> {
        return companyReviewService.createReview(review)
    }

    suspend fun updateReview(id: Long, review: CompanyReviewDto): Response<CompanyReviewDto> {
        return companyReviewService.updateReview(id, review)
    }

    suspend fun deleteReview(id: Long): Response<Unit> {
        return companyReviewService.deleteReview(id)
    }


    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> = try {
        val response = apiCall()
        Log.d("API", "Response: ${response.code()} - ${response.body()}")
        if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Response body was null"))
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("API", "Error: $errorBody")
            Result.failure(Exception("API error ${response.code()}: $errorBody"))
        }
    } catch (e: Exception) {
        Log.e("API", "Exception: ${e.message}", e)
        Result.failure(e)
    }
}