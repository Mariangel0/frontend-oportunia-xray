package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.domain.model.Student

class StudentProvider {
    companion object {
        private val studentList = listOf(
            Student(
                user = User(
                    id = 1,
                    createDate = "2025-01-01",
                    email = "student1@example.com",
                    enabled = true,
                    firstName = "Alex",
                    lastName = "Marin",
                    password = "12345",
                    tokenExpired = false
                ),
                description = "Software Engineering Student",
                premium = true,
                linkedinUrl = "https://linkedin.com/in/student1",
                githubUrl = "https://github.com/student1"
            ),
            Student(
                user = User(
                    id = 2,
                    createDate = "2023-02-01",
                    email = "student2@example.com",
                    enabled = true,
                    firstName = "Ana",
                    lastName = "Avenadanio",
                    password = "123456",
                    tokenExpired = false
                ),
                description = "Data Science Student",
                premium = false,
                linkedinUrl = "https://linkedin.com/in/student2",
                githubUrl = "https://github.com/student2"
            ),
            Student(
                user = User(
                    id = 3,
                    createDate = "2023-03-01",
                    email = "student3@example.com",
                    enabled = true,
                    firstName = "Maria",
                    lastName = "Avendanio",
                    password = "1234567",
                    tokenExpired = false
                ),
                description = "AI Researcher",
                premium = true,
                linkedinUrl = "https://linkedin.com/in/student3",
                githubUrl = "https://github.com/student3"
            )
        )

        fun findStudentById(id: Long): Student? {
            return studentList.find { it.user.id == id }
        }

        fun findAllStudents(): List<Student> {
            return studentList
        }
    }
}
