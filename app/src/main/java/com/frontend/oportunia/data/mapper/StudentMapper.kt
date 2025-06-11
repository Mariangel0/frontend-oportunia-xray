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
            user = dto.user?.let { userMapper.mapToDomain(it)},
            description = dto.description,
            premium = dto.premium,
            linkedinUrl = dto.linkedinUrl,
            githubUrl = dto.githubUrl,
            bornDate = dto.bornDate ,
            location = dto.location,
            userId = dto.userId
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
            user = domain.user?.let { userMapper.mapToDto(it) },
            description = domain.description,
            premium = domain.premium,
            linkedinUrl = domain.linkedinUrl,
            githubUrl = domain.githubUrl,
            bornDate = domain.bornDate,
            location = domain.location,
            userId = domain.userId
        )
    }

    fun mapToDtoRDto(domain: Student): StudentRDto {
        return StudentRDto(
            id = domain.id,
            user = userMapper.mapToDtoRDto(domain.user!!)
        )
    }


    fun mapToDtoList(students: List<Student>): List<StudentDto> {
        return students.map { mapToDto(it) }
    }

}