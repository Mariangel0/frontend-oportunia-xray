package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.UserDataSource
import com.frontend.oportunia.data.mapper.UserMapper
import com.frontend.oportunia.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

class UserRepositoryImpl(
    private val dataSource: UserDataSource,
    private val userMapper: UserMapper
) : UserRepository {

    companion object {
        private const val TAG = "UserRepository"
    }

    override suspend fun findAllUsers(): Result<List<User>> = runCatching {
        dataSource.getUsers().first().map { userDto ->
            userMapper.mapToDomain(userDto)
        }
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch users", throwable)

        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch users")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping users")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun findUserById(userId: Long): Result<User> = runCatching {
        val userDto = dataSource.getUserById(userId) ?: throw DomainError.UserError("User not found")
        userMapper.mapToDomain(userDto)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch user with ID: $userId", throwable)
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch user")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping user")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }
}
