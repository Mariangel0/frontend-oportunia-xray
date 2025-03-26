package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.StudentDataSource
import com.frontend.oportunia.data.mapper.StudentMapper
import kotlinx.coroutines.flow.first
import java.io.IOException

class StudentRepositoryImpl(
    private val dataSource: StudentDataSource,
    private val studentMapper: StudentMapper
) : StudentRepository {

    companion object {
        private const val TAG = "StudentRepository"
    }

    override suspend fun findAllStudents(): Result<List<Student>> = runCatching {
        dataSource.getStudents().first().map { studentDto ->
            studentMapper.mapToDomain(studentDto)
        }
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch students", throwable)

        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch students")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping students")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun findStudentById(studentId: Long): Result<Student> = runCatching {
        val studentDto = dataSource.getStudentById(studentId) ?: throw DomainError.StudentError("Student not found")
        studentMapper.mapToDomain(studentDto)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch student with ID: $studentId", throwable)
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch student")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping student")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }
}
