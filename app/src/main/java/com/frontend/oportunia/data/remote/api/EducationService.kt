package com.frontend.oportunia.data.remote.api

import com.frontend.oportunia.data.remote.dto.EducationDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EducationService {
    @GET("educations")
    suspend fun getAllEducations(): Response<List<EducationDto>>
    @GET("educations/{id}")
    suspend fun getEducationById(@Path("id") id: Long): Response<EducationDto>
    @POST("educations")
    suspend fun createEducation(@Body education: EducationDto): Response<EducationDto>
    @PUT("educations/{id}")
    suspend fun updateEducation(
        @Path("id") id: Long,
        @Body education: EducationDto
    ): Response<EducationDto>
    @DELETE("educations/{id}")
    suspend fun deleteEducation(@Path("id") id: Long): Response<Unit>

}