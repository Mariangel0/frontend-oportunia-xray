package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.Student

interface StudentRepository {
    suspend fun findAllStudents(): Result<List<Student>>
    suspend fun findStudentById(studentId: Long): Result<Student>
    suspend fun createStudent(student: Student): Result<Student>
}