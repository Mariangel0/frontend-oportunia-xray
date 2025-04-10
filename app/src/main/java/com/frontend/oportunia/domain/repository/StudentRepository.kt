package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.Ability
import com.frontend.oportunia.domain.model.Experience
import com.frontend.oportunia.domain.model.Student

interface StudentRepository {
    suspend fun findAllStudents(): Result<List<Student>>
    suspend fun findStudentById(studentId: Long): Result<Student>
    suspend fun getAbilitiesForStudent(studentId: Long): List<Ability>
    suspend fun getExperiencesForStudent(studentId: Long): List<Experience>
}