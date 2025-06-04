package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.CurriculumDataSource
import com.frontend.oportunia.data.mapper.CurriculumMapper
import com.frontend.oportunia.domain.error.DomainError
import com.frontend.oportunia.domain.model.Curriculum
import com.frontend.oportunia.domain.repository.CurriculumRepository
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.IOException
import javax.inject.Inject

class CurriculumRepositoryImpl  @Inject constructor(
    private val dataSource: CurriculumDataSource,
    private val curriculumMapper: CurriculumMapper
) : CurriculumRepository {

    companion object {
        private const val TAG = "CurriculumRepository"
    }

    override suspend fun findAllCurriculums(): Result<List<Curriculum>> = runCatching {
        dataSource.getCurriculums().first().map { curriculumDto ->
            curriculumMapper.mapToDomain(curriculumDto)
        }
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch curriculums", throwable)

        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch curriculums")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping curriculums")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError("An unknown error occurred")
        }
    }

    override suspend fun findCurriculumById(curriculumId: Long): Result<Curriculum> = runCatching {
        val curriculumDto = dataSource.getCurriculumById(curriculumId) ?: throw DomainError.CurriculumError("Curriculum not found")
        curriculumMapper.mapToDomain(curriculumDto)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch curriculum with ID: $curriculumId", throwable)
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch curriculum")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping curriculum")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError("An unknown error occurred")
        }
    }

    override suspend fun uploadCurriculum(fileData: ByteArray, studentId: Long): Result<Curriculum> = runCatching {
        val requestFile = RequestBody.create("application/pdf".toMediaTypeOrNull(), fileData)
        val body = MultipartBody.Part.createFormData("file", "curriculum.pdf", requestFile)
        val result = dataSource.uploadCurriculum(body, studentId)
        if (result.isSuccess) {
            val dto = result.getOrThrow()
            curriculumMapper.mapToDomain(dto)
        } else {
            throw result.exceptionOrNull() ?: Exception("Upload failed with unknown error")
        }
    }

}
