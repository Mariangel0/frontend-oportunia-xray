package com.frontend.oportunia.data.datasource
//
//import com.frontend.oportunia.data.datasource.model.AbilityDto
//import com.frontend.oportunia.data.datasource.model.ExperienceDto
//import com.frontend.oportunia.data.mapper.StudentMapper
//import com.frontend.oportunia.data.mapper.UserMapper
//import com.frontend.oportunia.domain.model.Student
//import com.frontend.oportunia.domain.model.User
//
//class tudentProvider {
//    companion object {
//        private val studentList = listOf(
//            Student(
//                user = User(
//                    id = 1,
//                    createDate = "2025-01-01",
//                    email = "student1@example.com",
//                    enabled = true,
//                    firstName = "Alex",
//                    lastName = "Marin",
//                    password = "12345",
//                    tokenExpired = false
//                ),
//                description = "Software Engineering Student",
//                premium = true,
//                linkedinUrl = "https://linkedin.com/in/student1",
//                githubUrl = "https://github.com/student1",
//                bornDate = "2002-04-06",
//                location = "Heredia"
//            ),
//            Student(
//                user = User(
//                    id = 2,
//                    createDate = "2023-02-01",
//                    email = "student2@example.com",
//                    enabled = true,
//                    firstName = "Ana",
//                    lastName = "Avenadanio",
//                    password = "123456",
//                    tokenExpired = false
//                ),
//                description = "Data Science Student",
//                premium = false,
//                linkedinUrl = "https://linkedin.com/in/student2",
//                githubUrl = "https://github.com/student2",
//                bornDate = "2003-03-27",
//                location = "Heredia"
//            ),
//            Student(
//                user = User(
//                    id = 3,
//                    createDate = "2023-03-01",
//                    email = "student3@example.com",
//                    enabled = true,
//                    firstName = "Maria",
//                    lastName = "Avendanio",
//                    password = "1234567",
//                    tokenExpired = false
//                ),
//                description = "AI Researcher",
//                premium = true,
//                linkedinUrl = "https://linkedin.com/in/student3",
//                githubUrl = "https://github.com/student3",
//                bornDate = "2003-03-27",
//                location = "Heredia"
//            )
//        )
//
//        fun findStudentById(id: Long): Student? {
//            return studentList.find { it.user.id == id }
//        }
//
//        fun findAllStudents(): List<Student> {
//            return studentList
//        }
//
//        private val userMapper = UserMapper()
//        private val studentMapper = StudentMapper(userMapper)
//        private val studentDtoList = studentList.map { studentMapper.mapToDto(it) }
//
//        private val abilities = listOf(
//            AbilityDto(id = 1, studentId = studentDtoList[0], name = "Liderazgo"),
//            AbilityDto(id = 2, studentId = studentDtoList[0], name = "Trabajo en equipo"),
//            AbilityDto(id = 3, studentId = studentDtoList[0], name = "Comunicación"),
//            AbilityDto(id = 4, studentId = studentDtoList[0], name = "Pensamiento crítico"),
//        )
//
//        private val experiences = listOf(
//            ExperienceDto(id = 1, studentId = studentDtoList[0], company = 1001, role = "Desarrollador Android", year = 2024),
//            ExperienceDto(id = 2, studentId = studentDtoList[0], company = 1002, role = "Backend Intern", year = 2023),
//            ExperienceDto(id = 3, studentId = studentDtoList[0], company = 1003, role = "Data Analyst Intern", year = 2023),
//            ExperienceDto(id = 4, studentId = studentDtoList[0], company = 1004, role = "Investigadora de IA", year = 2025),
//        )
//
//        fun findAbilitiesByStudentId(studentId: Long): List<AbilityDto> {
//            return abilities.filter { it.studentId.user.id == studentId }
//        }
//
//        fun findExperiencesByStudentId(studentId: Long): List<ExperienceDto> {
//            return experiences.filter { it.studentId.user.id == studentId }
//        }
//    }
//}
