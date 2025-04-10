package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.StudentDataSource
import com.frontend.oportunia.data.datasource.model.AbilityDto
import com.frontend.oportunia.data.datasource.model.ExperienceDto
import com.frontend.oportunia.data.mapper.AbilityMapper
import com.frontend.oportunia.data.mapper.ExperienceMapper
import com.frontend.oportunia.data.mapper.StudentMapper
import com.frontend.oportunia.data.repository.StreakRepositoryImpl.Companion
import com.frontend.oportunia.domain.error.DomainError
import com.frontend.oportunia.domain.model.Ability
import com.frontend.oportunia.domain.model.Experience
import com.frontend.oportunia.domain.model.Student
import com.frontend.oportunia.domain.repository.StudentRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

class StudentRepositoryImpl(
    private val dataSource: StudentDataSource,
    private val studentMapper: StudentMapper,
    private val abilityMapper: AbilityMapper,
    private val experienceMapper: ExperienceMapper
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
            else -> throw DomainError.UnknownError("An unknown error occurred")
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
            else -> throw DomainError.UnknownError("An unknown error occurred")
        }
    }
    override suspend fun getAbilitiesForStudent(studentId: Long): List<Ability> = runCatching {
        dataSource.getAbilitiesForStudent(studentId).map { abilityMapper.mapToDomain(it) }
    }.getOrElse {
        Log.e(TAG, "Error fetching abilities", it)
        emptyList()
    }

    override suspend fun getExperiencesForStudent(studentId: Long): List<Experience> = runCatching {
        dataSource.getExperiencesForStudent(studentId).map { experienceMapper.mapToDomain(it) }
    }.getOrElse {
        Log.e(TAG, "Error fetching experiences", it)
        emptyList()
    }
}
