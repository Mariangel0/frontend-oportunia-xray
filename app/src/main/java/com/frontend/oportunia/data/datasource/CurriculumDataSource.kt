package com.frontend.oportunia.data.datasource

import android.util.Log
import com.frontend.oportunia.data.remote.api.CurriculumService
import com.frontend.oportunia.data.remote.dto.CurriculumDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class CurriculumDataSource @Inject constructor(
    private val curriculumService: CurriculumService
) {

    companion object {
        private const val TAG = "CurriculumDataSource"
    }

    suspend fun getCurriculums(): Flow<List<CurriculumDto>> = flow {
        val result = safeApiCall { curriculumService.getCurriculums() }
        if (result.isSuccess) {
            emit(result.getOrThrow())
        } else {
            Log.e(TAG, "Failed to fetch curriculums: ${result.exceptionOrNull()}")
            throw result.exceptionOrNull() ?: Exception("Unknown error")
        }
    }

    suspend fun getCurriculumById(id: Long): CurriculumDto? {
        val result = safeApiCall { curriculumService.getCurriculumById(id) }
        return if (result.isSuccess) {
            result.getOrNull()
        } else {
            Log.e(TAG, "Failed to fetch curriculum with id $id: ${result.exceptionOrNull()}")
            throw result.exceptionOrNull() ?: Exception("Unknown error")
        }
    }

    suspend fun insertCurriculum(curriculumDto: CurriculumDto) {
        // Implementa si tienes endpoint POST correspondiente
        throw NotImplementedError("Insert curriculum not implemented")
    }

    suspend fun updateCurriculum(curriculumDto: CurriculumDto) {
        // Implementa si tienes endpoint PUT correspondiente
        throw NotImplementedError("Update curriculum not implemented")
    }

    suspend fun deleteCurriculum(curriculumDto: CurriculumDto) {
        // Implementa si tienes endpoint DELETE correspondiente
        throw NotImplementedError("Delete curriculum not implemented")
    }

    suspend fun uploadCurriculum(
        filePart: MultipartBody.Part,
        studentId: Long
    ): Result<CurriculumDto> = safeApiCall {
        curriculumService.uploadCurriculum(studentId, filePart)
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
        return try {
            val response = apiCall()
            Log.d(TAG, "Response code: ${response.code()}")
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Response body was null"))
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(TAG, "API error: $errorBody")
                Result.failure(Exception("API error ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in API call: ${e.message}", e)
            Result.failure(e)
        }
    }
}
