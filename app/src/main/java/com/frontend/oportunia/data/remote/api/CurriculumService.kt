package com.frontend.oportunia.data.remote.api

import com.frontend.oportunia.data.remote.dto.CurriculumDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface CurriculumService {

    @GET("curriculums")
    suspend fun getCurriculums(): Response<List<CurriculumDto>>

    @GET("curriculums/{id}")
    suspend fun getCurriculumById(@Path("id") id: Long): Response<CurriculumDto?>

    @Multipart
    @POST("curriculums/upload/{studentId}")
    suspend fun uploadCurriculum(
        @Path("studentId") studentId: Long,
        @Part file: MultipartBody.Part
    ): Response<CurriculumDto>

    @PUT("curriculums/{id}")
    suspend fun updateCurriculum(
        @Path("id") id: Long,
        @Body curriculumDto: CurriculumDto
    ): Response<CurriculumDto>

    @DELETE("curriculums/{id}")
    suspend fun deleteCurriculum(@Path("id") id: Long): Response<Unit>
}
