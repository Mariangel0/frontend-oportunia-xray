package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.datasource.model.StudentDto
import kotlinx.coroutines.flow.Flow

interface StudentDataSource {
    suspend fun getStudents(): Flow<List<StudentDto>>
    suspend fun getStudentById(id: Long): StudentDto?
    suspend fun insertStudent(studentDto: StudentDto)
    suspend fun updateStudent(studentDto: StudentDto)
    suspend fun deleteStudent(studentDto: StudentDto)
}