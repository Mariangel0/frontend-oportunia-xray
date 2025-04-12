package com.frontend.oportunia.data.remote.api

import com.frontend.oportunia.data.remote.dto.AbilityDto
import com.frontend.oportunia.data.remote.dto.StudentDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface StudentService {

    @GET("students")
    suspend fun getAllStudents(): Response<List<StudentDto>>

    @GET("students/{id}")
    suspend fun geStudentById(@Path("id") id: Long): Response<StudentDto>

    @GET("students")
    suspend fun getStudentByUserId(@Query("user.id") userId: Long): Response<StudentDto>

    @POST("students")
    suspend fun createStudent(@Body student: StudentDto): Response<StudentDto>

    @PUT("students/{id}")
    suspend fun updateStudent(
        @Path("id") id: Long,
        @Body student: StudentDto
    ): Response<StudentDto>

    @DELETE("students/{id}")
    suspend fun deleteStudent(@Path("id") id: Long): Response<Unit>
}