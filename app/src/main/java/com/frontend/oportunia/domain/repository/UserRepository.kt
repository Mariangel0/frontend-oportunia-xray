package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.User

interface UserRepository {


    suspend fun createUser(user: User): Result<User>
    suspend fun findAllUsers(): Result<List<User>>
    suspend fun findUserById(userId: Long): Result<User>
    suspend fun getCurrentUser(): Result<User?>
    suspend fun updateUser(user: User): Result<User>
    suspend fun deleteUser(id: Long): Result<Unit>
}