package com.frontend.oportunia.data.remote.interceptor

import com.frontend.oportunia.data.local.AuthPreferences
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val authPreferences: AuthPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Skip authentication for login and registration endpoints
        if (originalRequest.url.encodedPath.contains("auth/login") ||
            originalRequest.url.encodedPath.contains("auth/register") ||
            originalRequest.url.encodedPath.contains("/users") ||
            originalRequest.url.encodedPath.contains("/students")
        ) {
            return chain.proceed(originalRequest)
        }

        // Get the token from AuthPreferences
        val token = runBlocking {
            authPreferences.getAuthToken()
        }

        // If no token is available, proceed with the original request
        if (token.isNullOrBlank()) {
            return chain.proceed(originalRequest)
        }

        // Add the token to the request header
        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        return chain.proceed(authenticatedRequest)
    }
}