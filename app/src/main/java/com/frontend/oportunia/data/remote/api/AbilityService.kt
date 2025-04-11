package com.frontend.oportunia.data.remote.api;

import com.frontend.oportunia.data.remote.dto.AbilityDto;

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

public interface AbilityService {

    @GET("abilities")
    suspend fun getAllAbilities(): Response<List<AbilityDto>>

    @GET("abilities/{id}")
    suspend fun getAbilityById(@Path("id") id: Long): Response<AbilityDto>

    @POST("abilities")
    suspend fun createAbility(@Body ability: AbilityDto): Response<AbilityDto>

    @GET("abilities")
    suspend fun getAbilitiesByStudentId(@Query("studentId") studentId: Long): Response<List<AbilityDto>>

    @PUT("abilities/{id}")
    suspend fun updateAbility(
        @Path("id") id: Long,
        @Body review: AbilityDto
    ): Response<AbilityDto>

    @DELETE("abilities/{id}")
    suspend fun deleteAbility(@Path("id") id: Long): Response<Unit>

}