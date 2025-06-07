package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.StudentDto
import com.frontend.oportunia.domain.model.Student
import javax.inject.Inject

class StudentMapper @Inject constructor(
    private val userMapper: UserMapper
){

    fun mapToDomain(dto: StudentDto): Student {
        return Student(
          //  user = userMapper.mapToDomain(dto.user),
            id = dto.id,
            description = dto.description,
            premium = dto.premium,
            linkedinUrl = dto.linkedinUrl,
            githubUrl = dto.githubUrl,
            bornDate = dto.bornDate ,
            location = dto.location
        )
    }

    fun mapToDomainList(studentDto: List<StudentDto>): List<Student> {
        return studentDto.map { mapToDomain(it) }
    }

    fun mapToDto(domain: Student): StudentDto {
        return StudentDto(
          //  user = userMapper.mapToDto(domain.user),
            id = domain.id,
            description = domain.description,
            premium = domain.premium,
            linkedinUrl = domain.linkedinUrl,
            githubUrl = domain.githubUrl,
            bornDate = domain.bornDate,
            location = domain.location
        )
    }

    fun mapToDtoList(students: List<Student>): List<StudentDto> {
        return students.map { mapToDto(it) }
    }

}