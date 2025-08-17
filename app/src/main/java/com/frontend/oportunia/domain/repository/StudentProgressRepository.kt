package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.StudentProgress

interface StudentProgressRepository {
    suspend fun findStudentProgressById(studentProgressId: Long): Result<StudentProgress>
    suspend fun findAllStudentProgress(): Result<List<StudentProgress>>
}