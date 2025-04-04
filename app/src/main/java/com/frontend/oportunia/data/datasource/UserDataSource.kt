package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    suspend fun getUsers(): Flow<List<UserDto>>
    suspend fun getUserById(id: Long): UserDto?
    suspend fun insertUser(userDto: UserDto)
    suspend fun updateUser(userDto: UserDto)
    suspend fun deleteUser(userDto: UserDto)

    suspend fun getStudents(): Flow<List<UserDto>> // Obtener solo estudiantes
    suspend fun getAdmins(): Flow<List<UserDto>> // Obtener solo administradores
}