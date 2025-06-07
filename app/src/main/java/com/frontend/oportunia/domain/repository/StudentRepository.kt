package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.Student

interface StudentRepository {
    suspend fun findAllStudents(): Result<List<Student>>
    suspend fun findStudentById(studentId: Long): Result<Student>
    suspend fun createStudent(student: Student): Result<Student>
    suspend fun findStudentByUserId(userId: Long): Result<Student>
    suspend fun updateStudent(student: Student): Result<Student>


    suspend fun deleteStudent(studentId: Long): Result<Unit>

}