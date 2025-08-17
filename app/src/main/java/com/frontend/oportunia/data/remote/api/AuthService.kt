package com.frontend.oportunia.data.remote.api

import com.frontend.oportunia.data.remote.dto.auth.AuthRequestDto
import com.frontend.oportunia.data.remote.dto.auth.AuthResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    /**
     * Authenticates a user with the provided credentials
     *
     * @param request Authentication credentials
     * @return Authentication response containing token
     */
    @POST("users/login")
    suspend fun login(@Body request: AuthRequestDto): Response<AuthResponseDto>

    /**
     * Logs out the current user session
     */
    @POST("users/logout") //TODO: Pending in backend
    suspend fun logout(): Response<Unit>
}