package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.StudentDto

class StudentMapper(
    private val userMapper: UserMapper
){

    fun mapToDomain(dto: StudentDto): Student = Student(
        user = userMapper.mapToDomain(dto.user),
        description = dto.description,
        premium = dto.premium,
        linkedinUrl = dto.linkedinUrl,
        githubUrl = dto.githubUrl
    )

    fun mapToDto(domain: Student): StudentDto = StudentDto(
        user = userMapper.mapToDto(domain.user),
        description = domain.description,
        premium = domain.premium,
        linkedinUrl = domain.linkedinUrl,
        githubUrl = domain.githubUrl
    )
}