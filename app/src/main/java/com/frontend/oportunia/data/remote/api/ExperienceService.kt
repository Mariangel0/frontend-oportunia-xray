package com.frontend.oportunia.data.remote.api

import com.frontend.oportunia.data.remote.dto.AbilityDto
import com.frontend.oportunia.data.remote.dto.ExperienceDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


 interface ExperienceService {

    @GET("experiences")
    suspend fun getAllExperiences(): Response<List<ExperienceDto>>

    @GET("experiences/{id}")
    suspend fun getExperienceById(@Path("id") id: Long): Response<ExperienceDto>

    @POST("experiences")
    suspend fun createExperience(@Body ability: ExperienceDto): Response<ExperienceDto>

    @GET("experiences/student/{userId}")
    suspend fun getExperiencesByStudentId(@Path("userId") userId: Long): Response<List<ExperienceDto>>

    @PUT("experiences/{id}")
    suspend fun updateExperience(
        @Path("id") id: Long,
        @Body review: ExperienceDto
    ): Response<ExperienceDto>

    @DELETE("experiences/{id}")
    suspend fun deleteExperience(@Path("id") id: Long): Response<Unit>

}