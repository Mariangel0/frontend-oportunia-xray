package com.frontend.oportunia.data.remote.api

import com.frontend.oportunia.data.remote.dto.AdviceDto
import com.frontend.oportunia.data.remote.dto.ExperienceDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AdviceService {

    @GET("advices")
    suspend fun getAllAdvices(): Response<List<AdviceDto>>

    @GET("advices/{id}")
    suspend fun getAdviceById(@Path("id") id: Long): Response<AdviceDto>

    @POST("advices")
    suspend fun createAdvice(@Body advice: AdviceDto): Response<AdviceDto>

    @GET("advices")
    suspend fun getAdvicesByStudentId(@Query("studentId.id") studentId: Long): Response<List<AdviceDto>>

    @PUT("advices/{id}")
    suspend fun updateAdvice(
        @Path("id") id: Long,
        @Body advice: AdviceDto
    ): Response<AdviceDto>

    @DELETE("advices/{id}")
    suspend fun deleteAdvice(@Path("id") id: Long): Response<Unit>
}