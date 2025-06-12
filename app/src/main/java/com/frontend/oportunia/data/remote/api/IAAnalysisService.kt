package com.frontend.oportunia.data.remote.api

import com.frontend.oportunia.data.remote.dto.IAAnalysisDto
import retrofit2.Response
import retrofit2.http.*

interface IAAnalysisService {

    @GET("ia_analyses")
    suspend fun getAllIAAnalyses(): Response<List<IAAnalysisDto>>

    @GET("ia_analyses/{id}")
    suspend fun getIAAnalysisById(@Path("id") id: Long): Response<IAAnalysisDto>

    @POST("ia_analyses")
    suspend fun createIAAnalysis(@Body input: IAAnalysisDto): Response<IAAnalysisDto>

    @DELETE("ia_analyses/{id}")
    suspend fun deleteIAAnalysis(@Path("id") id: Long): Response<Unit>
}