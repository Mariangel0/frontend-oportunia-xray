package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.StudentProgressDataSource
import com.frontend.oportunia.data.mapper.StudentProgressMapper
import com.frontend.oportunia.domain.error.DomainError
import com.frontend.oportunia.domain.model.StudentProgress
import com.frontend.oportunia.domain.repository.StudentProgressRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

class StudentProgressRepositoryImpl(
    private val dataSource: StudentProgressDataSource,
    private val studentProgressMapper: StudentProgressMapper
) : StudentProgressRepository {

    companion object {
        private const val TAG = "StudentProgressRepository"
    }

    override suspend fun findAllStudentProgress(): Result<List<StudentProgress>> = runCatching {
        dataSource.getStudentProgress().first().map { studentProgressDto ->
            studentProgressMapper.mapToDomain(studentProgressDto)
        }
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch student progress", throwable)

        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch student progress")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping student progress")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError("An unknown error occurred")
        }
    }

    override suspend fun findStudentProgressById(studentProgressId: Long): Result<StudentProgress> = runCatching {
        val studentProgressDto = dataSource.getStudentProgressById(studentProgressId) ?: throw DomainError.StudentProgressError("Student progress not found")
        studentProgressMapper.mapToDomain(studentProgressDto)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch student progress with ID: $studentProgressId", throwable)
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch student progress")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping student progress")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError("An unknown error occurred")
        }
    }
}

