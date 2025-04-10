package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.datasource.model.AbilityDto
import com.frontend.oportunia.data.datasource.model.ExperienceDto
import com.frontend.oportunia.data.datasource.model.StudentDto
import kotlinx.coroutines.flow.Flow

interface StudentDataSource {
    suspend fun getStudents(): Flow<List<StudentDto>>
    suspend fun getStudentById(id: Long): StudentDto?
    suspend fun insertStudent(studentDto: StudentDto)
    suspend fun updateStudent(studentDto: StudentDto)
    suspend fun deleteStudent(studentDto: StudentDto)
    suspend fun getAbilitiesForStudent(studentId: Long): List<AbilityDto>
    suspend fun getExperiencesForStudent(studentId: Long): List<ExperienceDto>
}