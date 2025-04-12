package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.StudentDataSource
import com.frontend.oportunia.data.mapper.StudentMapper
import com.frontend.oportunia.domain.error.DomainError
import com.frontend.oportunia.domain.model.Company
import com.frontend.oportunia.domain.model.Student
import com.frontend.oportunia.domain.model.User
import com.frontend.oportunia.domain.repository.StudentRepository
import kotlinx.coroutines.flow.first
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class StudentRepositoryImpl @Inject constructor(
    private val dataSource: StudentDataSource,
    private val studentMapper: StudentMapper
) : StudentRepository {


    override suspend fun findAllStudents(): Result<List<Student>> {
        return try {
            dataSource.getAllStudents().map { studentDtos ->
                studentMapper.mapToDomainList(studentDtos)
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Cannot connect to server. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching tasks: ${e.message}"))
        }
    }

    override suspend fun findStudentById(studentId: Long): Result<Student> =
        dataSource.getStudentById(studentId).map { studentDto ->
            studentMapper.mapToDomain(studentDto)
        }

    override suspend fun createStudent(student: Student): Result<Student> {
        val studentDto = studentMapper.mapToDto(student)
        return try {
            val response = dataSource.createStudent(studentDto)
            if (response.isSuccessful && response.body() != null) {
                Result.success(studentMapper.mapToDomain(response.body()!!))
            } else {
                Result.failure(Exception("Error en la creación del estudiante: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun findStudentByUserId(userId: Long): Result<Student> =
        dataSource.getStudentByUserId(userId).map { studentDto ->
            studentMapper.mapToDomain(studentDto)
        }



}
