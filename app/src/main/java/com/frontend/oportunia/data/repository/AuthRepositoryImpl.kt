package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.UserDataSource
import com.frontend.oportunia.data.local.AuthPreferences
import com.frontend.oportunia.data.mapper.UserMapper
import com.frontend.oportunia.data.remote.AuthRemoteDataSource
import com.frontend.oportunia.data.remote.dto.UserDto
import com.frontend.oportunia.domain.repository.AuthRepository
import com.frontend.oportunia.domain.model.Credentials
import com.frontend.oportunia.domain.model.User
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authPreferences: AuthPreferences,
    private val userDataSource: UserDataSource,
    private val userMapper: UserMapper
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<Unit> {
        return try {
            Log.d("AuthRepositoryImpl", "Login attempt for user: $username")
            val credentials = Credentials(username, password)

            authRemoteDataSource.login(credentials)
                .onSuccess { authResult ->
                    val token = authResult.token
                    if (token.isNotBlank()) {
                        Log.d("AuthRepositoryImpl", "Login successful, token: ${token.take(10)}...")

                        // Guardar token y username
                        authPreferences.saveAuthToken(token)
                        authPreferences.saveUsername(username)

                        // 🔹 Obtener el usuario desde backend y guardarlo localmente
                        try {
                            val response: Response<UserDto> = userDataSource.getCurrentUser()
                            if (response.isSuccessful && response.body() != null) {
                                val userDto = response.body()!!
                                authPreferences.saveUser(userDto)
                                Log.d("AuthRepositoryImpl", "UserDto saved after login: ${userDto.email}")
                            } else {
                                Log.e("AuthRepositoryImpl", "Failed to fetch current user: ${response.code()} ${response.message()}")
                            }
                        } catch (e: UnknownHostException) {
                            Log.e("AuthRepositoryImpl", "Network error: Cannot connect to server.", e)
                        } catch (e: Exception) {
                            Log.e("AuthRepositoryImpl", "Error fetching current user after login: ${e.message}", e)
                        }

                    } else {
                        Log.e("AuthRepositoryImpl", "Received empty token in auth result")
                    }
                }
                .onFailure { error ->
                    Log.e("AuthRepositoryImpl", "Login failed: ${error.message}")
                }
                .map { } // convierte Result<AuthResult> a Result<Unit>

        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Login exception: ${e.message}", e)
            Result.failure(e)
        }
    }


    override suspend fun logout(): Result<Unit> {
        return try {
            Log.d("AuthRepositoryImpl", "Attempting to logout")
            // Call remote logout if needed
            authRemoteDataSource.logout()
                .onSuccess {
                    Log.d("AuthRepositoryImpl", "Remote logout successful")
                }
                .onFailure { error ->
                    Log.e("AuthRepositoryImpl", "Remote logout failed: ${error.message}")
                    // Continue with local logout even if remote fails
                }

            // Clear local auth state regardless of remote result
            authPreferences.clearAuthData()
            Log.d("AuthRepositoryImpl", "Local auth data cleared")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Logout exception: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun isAuthenticated(): Result<Boolean> {
        return try {
            val token = authPreferences.getAuthToken()
            val isAuthenticated = token != null && token.isNotEmpty()
            Log.d("AuthRepositoryImpl", "Authentication check: $isAuthenticated")
            Result.success(isAuthenticated)
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Auth check exception: ${e.message}", e)
            Result.failure(e)
        }
    }


    override suspend fun getUser(): Result<String?> {
        return try {
            val username = authPreferences.getUsername()
            Log.d("AuthRepositoryImpl", "Current user: $username")
            Result.success(username)
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Get current user exception: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun getCurrentUser(): Result<User?> {
        return try {
            val userDto = authPreferences.getSavedUser()
            if (userDto != null) {
                val user = userMapper.mapToDomain(userDto)
                Log.d("AuthRepositoryImpl", "Current user: $user")
                Result.success(user)
            } else {
                Log.d("AuthRepositoryImpl", "No current user found")
                Result.success(null)
            }
        }
        catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Get current user exception: ${e.message}", e)
            Result.failure(e)
        }

    }


}