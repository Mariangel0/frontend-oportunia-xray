package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.StudentDto
import com.frontend.oportunia.data.remote.dto.StudentRDto
import com.frontend.oportunia.domain.model.Student
import javax.inject.Inject

class StudentMapper @Inject constructor(
    private val userMapper: UserMapper
){

    fun mapToDomain(dto: StudentDto): Student {
        return Student(
            id = dto.id,
            description = dto.description,
            premium = dto.premium,
            linkedinUrl = dto.linkedinUrl,
            githubUrl = dto.githubUrl,
            bornDate = dto.bornDate,
            location = dto.location,
            user = dto.user?.let { userMapper.mapToDomain(it)},
        )
    }

    fun mapToDomainRDto(dto: StudentRDto): Student {
        return Student(
            id = dto.id,
            user = dto.user.let { userMapper.mapToDomainRDto(it) }
            )
    }

    fun mapToDomainList(studentDto: List<StudentDto>): List<Student> {
        return studentDto.map { mapToDomain(it) }
    }

    fun mapToDto(domain: Student): StudentDto {
        return StudentDto(
            id = domain.id,
            description = domain.description,
            premium = domain.premium,
            linkedinUrl = domain.linkedinUrl,
            githubUrl = domain.githubUrl,
            bornDate = domain.bornDate,
            location = domain.location,
            user = domain.user?.let { userMapper.mapToDto(it) },
        )
    }

    fun mapToDtoRDto(domain: Student): StudentRDto {
        val user = domain.user ?: throw IllegalStateException("Student.user es null al mapear a StudentRDto")
        return StudentRDto(
            id = domain.id,
            user = userMapper.mapToDtoRDto(user)
        )
    }


    fun mapToDtoList(students: List<Student>): List<StudentDto> {
        return students.map { mapToDto(it) }
    }

}