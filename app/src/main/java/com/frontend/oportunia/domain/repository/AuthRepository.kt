package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.User


interface AuthRepository {

    suspend fun login(username: String, password: String): Result<Unit>

    suspend fun logout(): Result<Unit>

    suspend fun isAuthenticated(): Result<Boolean>
    suspend fun getUser(): Result<String?>

    suspend fun getCurrentUser(): Result<User?>
}