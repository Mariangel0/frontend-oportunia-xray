package com.frontend.oportunia.data.remote.api

import com.frontend.oportunia.data.remote.dto.AbilityDto
import com.frontend.oportunia.data.remote.dto.StreakDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StreakService {

    @GET("streaks/student/{studentId}")
    suspend fun getStreakByStudentId(@Path("studentId") studentId: Long): Response<StreakDto>

    @GET("streaks")
    suspend fun getAllStreaks(): Response<List<StreakDto>>

    @GET("streaks/{id}")
    suspend fun getStreakById(@Path("id") id: Long): Response<StreakDto>


}