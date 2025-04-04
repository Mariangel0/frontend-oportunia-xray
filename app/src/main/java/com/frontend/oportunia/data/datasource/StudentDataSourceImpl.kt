package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.dto.StudentDto
import com.frontend.oportunia.data.mapper.StudentMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StudentDataSourceImpl(
    private val studentMapper: StudentMapper
) : StudentDataSource {

    override suspend fun getStudents(): Flow<List<StudentDto>> = flow {
        val students = StudentProvider.findAllStudents()
        emit(students.map { studentMapper.mapToDto(it) })
    }

    override suspend fun getStudentById(id: Long): StudentDto? = StudentProvider.findStudentById(id)?.let {
        studentMapper.mapToDto(it)
    }

    override suspend fun insertStudent(studentDto: StudentDto) {
    }

    override suspend fun updateStudent(studentDto: StudentDto) {
    }

    override suspend fun deleteStudent(studentDto: StudentDto) {
    }
}