package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.dto.StudentProgressDto
import kotlinx.coroutines.flow.Flow

interface StudentProgressDataSource {
    suspend fun getStudentProgress(): Flow<List<StudentProgressDto>>
    suspend fun getStudentProgressById(id: Long): StudentProgressDto?
    suspend fun insertStudentProgress(studentProgressDto: StudentProgressDto)
    suspend fun updateStudentProgress(studentProgressDto: StudentProgressDto)
    suspend fun deleteStudentProgress(studentProgressDto: StudentProgressDto)
}