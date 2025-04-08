package com.frontend.oportunia.data.repository

import com.frontend.oportunia.data.datasource.UserDataSource
import com.frontend.oportunia.data.mapper.UserMapper
import com.frontend.oportunia.domain.model.User
import com.frontend.oportunia.domain.repository.UserRepository
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


//    override suspend fun findUserById(userId: Long): Result<User> = runCatching {
//        val userDto = dataSource.getUserById(userId) ?: throw DomainError.UserError("User not found")
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
}
