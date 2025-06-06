package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.User

interface UserRepository {
    suspend fun findAllUsers(): Result<List<User>>
    suspend fun findUserById(userId: Long): Result<User>
    suspend fun getCurrentUser(): Result<User?>

}