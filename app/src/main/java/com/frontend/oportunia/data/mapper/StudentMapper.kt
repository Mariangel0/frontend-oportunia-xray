package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.StudentDto

class StudentMapper {
    fun mapToDomain(dto: StudentDto): Student = Student(
        id = dto.id,
        description = dto.description,
        premium = dto.premium,
        linkedinUrl = dto.linkedinUrl,
        githubUrl = dto.githubUrl
    )

    fun mapToDto(domain: Student): StudentDto = StudentDto(
        id = domain.id,
        description = domain.description,
        premium = domain.premium,
        linkedinUrl = domain.linkedinUrl,
        githubUrl = domain.githubUrl
    )
}