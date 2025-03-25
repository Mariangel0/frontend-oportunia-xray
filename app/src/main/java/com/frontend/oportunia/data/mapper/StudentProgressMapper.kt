package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.StudentProgressDto

class StudentProgressMapper {

    fun mapToDomain(dto: StudentProgressDto): StudentProgress = StudentProgress(
        id = dto.id,
        studentId = dto.studentId,
        totalInterviews = dto.totalInterviews,
        averageScore = dto.averageScore,
        uploadedCl = dto.uploadedCl,
        lastActivity = dto.lastActivity
    )

    fun mapToDto(domain: StudentProgress): StudentProgressDto = StudentProgressDto(
        id = domain.id,
        studentId = domain.studentId,
        totalInterviews = domain.totalInterviews,
        averageScore = domain.averageScore,
        uploadedCl = domain.uploadedCl,
        lastActivity = domain.lastActivity
    )
}