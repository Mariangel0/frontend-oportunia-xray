package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.UserDataSource
import com.frontend.oportunia.data.mapper.UserMapper
import com.frontend.oportunia.data.remote.dto.UserDto
import com.frontend.oportunia.domain.model.User
import com.frontend.oportunia.domain.repository.UserRepository
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource,
    private val userMapper: UserMapper
) : UserRepository {


    override suspend fun findAllUsers(): Result<List<User>> {
        return try {
            dataSource.getAllUsers().map { userDtos ->
                userMapper.mapToDomainList(userDtos)
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Cannot connect to server. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching tasks: ${e.message}"))
        }
    }

    override suspend fun findUserById(userId: Long): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): Result<User?> {
        return try {
            val response: Response<UserDto> = dataSource.getCurrentUser()
            if (response.isSuccessful && response.body() != null) {
                val userDto: UserDto = response.body()!!
                val userDomain: User = userMapper.mapToDomain(userDto)
                Result.success(userDomain)
            } else {
                Result.failure(Exception("Error fetching current user: ${response.code()} ${response.message()}"))
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Cannot connect to server."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createUser(user: User): Result<User> {
        return try {
            val userDto = userMapper.mapToDto(user)
            val response = dataSource.createUser(userDto)
            if (response.isSuccessful) {
                val createdUserDto = response.body()
                if (createdUserDto != null) {
                    val createdUserDomain = userMapper.mapToDomain(createdUserDto)
                    Result.success(createdUserDomain)
                } else {
                    Result.failure(Exception("Error creating user: Response body is null"))

                }
            } else {
                Result.failure(Exception("Error creating user: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {

            Log.e("UserRepository", "Error creating user", e)
            Result.failure(e)
        }

    }


//    override suspend fun findUserById(userId: Long): Result<User> = runCatching {
//        val userDto = dataSource.getUserById(userId)
//        userMapper.mapToDomain(userDto)
//    }.recoverCatching { throwable ->
//        Log.e(TAG, "Failed to fetch user with ID: $userId", throwable)
//        when (throwable) {
//            is IOException -> throw DomainError.NetworkError("Failed to fetch user")
//            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping user")
//            is DomainError -> throw throwable
//            else -> throw DomainError.UnknownError("An unknown error occurred")
//        }
//    }

    override suspend fun updateUser(user: User): Result<User> {
        return try {
            val userDto = userMapper.mapToDto(user)
            val response = dataSource.updateUser(user.id!!, userDto)
            if (response.isSuccessful) {
                val updatedUser = userMapper.mapToDomain(response.body()!!)
                Result.success(updatedUser)
            } else {
                throw Exception("Error updating user: ${response.code()} ")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
