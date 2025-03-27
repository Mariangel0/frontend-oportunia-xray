package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.domain.model.Education

class EducationProvider {
    companion object {
        private val educationList = listOf(
            Education(id = 1, studentId = StudentProvider.findStudentById(1)!!, name = "Computer Science", institution = "UNA", year = 2024),
            Education(id = 2, studentId = StudentProvider.findStudentById(2)!!, name = "Mechanical Engineering", institution = "TEC", year = 2025)
        )

        fun findEducationById(id: Long): Education? {
            return educationList.find { it.id == id }
        }

        fun findAllEducations(): List<Education> {
            return educationList
        }
    }
}
