package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.api.UserService
import com.frontend.oportunia.data.remote.dto.CompanyDto
import com.frontend.oportunia.data.remote.dto.UserDto
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val userService: UserService
) {
    suspend fun getAllUsers(): Result<List<UserDto>> = safeApiCall {
        userService.getAllUsers()
    }
    suspend fun getUserById(id: Long): Result<UserDto>  = safeApiCall {
        userService.getUserById(id)
    }
    suspend fun createUser(userDto: UserDto): Response<UserDto> {
        return userService.createUser(userDto)
    }

    suspend fun updateUser(id: Long, userDto: UserDto): Response<UserDto> {
        return userService.updateUser(id, userDto)
    }

    suspend fun deleteUser(id: Long): Response<Unit> {
        return userService.deleteUser(id)
    }

//    suspend fun getStudents(): Flow<List<UserDto>> // Obtener solo estudiantes
////    suspend fun getAdmins(): Flow<List<UserDto>> // Obtener solo administradores

    suspend fun getCurrentUser(): Response<UserDto> {
        return  userService.getCurrentUser()
    }



    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> = try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Response body was null"))
        } else {
            val errorBody = response.errorBody()?.string()
            Result.failure(Exception("API error ${response.code()}: $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}