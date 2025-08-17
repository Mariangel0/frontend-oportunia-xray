package com.frontend.oportunia.data.datasource


import com.frontend.oportunia.data.remote.api.AbilityService
import com.frontend.oportunia.data.remote.dto.AbilityDto
import retrofit2.Response
import javax.inject.Inject

class AbilityDataSource @Inject constructor(
    private val abilityService: AbilityService
) {

    suspend fun getAllAbilities(): Result<List<AbilityDto>> = safeApiCall {
        abilityService.getAllAbilities()
    }

    suspend fun getAbilityById(id: Long): Result<AbilityDto> = safeApiCall {
        abilityService.getAbilityById(id)
    }

    suspend fun getAbilitiesByStudentId(studentId: Long): Result<List<AbilityDto>> = safeApiCall {
        abilityService.getAbilitiesByStudentId(studentId)
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