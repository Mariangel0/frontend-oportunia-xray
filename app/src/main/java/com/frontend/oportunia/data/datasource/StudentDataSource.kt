package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.api.StudentService
import com.frontend.oportunia.data.remote.dto.StudentDto
import retrofit2.Response
import javax.inject.Inject

class StudentDataSource @Inject constructor(
    private val studentService: StudentService
) {
    suspend fun getAllStudents(): Result<List<StudentDto>> = safeApiCall {
        studentService.getAllStudents()
    }
    suspend fun getStudentById(id: Long): Result<StudentDto>  = safeApiCall {
        studentService.geStudentById(id)
    }
    suspend fun createStudent(studentDto: StudentDto): Response<StudentDto> {
        return studentService.createStudent(studentDto)
    }
    suspend fun getStudentByUserId(userId: Long): Result<StudentDto> = safeApiCall {
        studentService.getStudentByUserId(userId)
    }

    suspend fun updateStudent(id: Long, studentDto: StudentDto): Response<StudentDto> {
        return studentService.updateStudent(id, studentDto)
    }

    suspend fun deleteStudent(id: Long): Response<Unit> {
        return studentService.deleteStudent(id)
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