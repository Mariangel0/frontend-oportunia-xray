package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.StudentProgressDto
import com.frontend.oportunia.domain.model.StudentProgress

class StudentProgressMapper(
    private val studentMapper: StudentMapper
) {

    fun mapToDomain(dto: StudentProgressDto): StudentProgress = StudentProgress(
        id = dto.id,
        studentId = studentMapper.mapToDomain(dto.studentId),
        totalInterviews = dto.totalInterviews,
        averageScore = dto.averageScore,
        uploadedCl = dto.uploadedCl,
        lastActivity = dto.lastActivity
    )

    fun mapToDto(domain: StudentProgress): StudentProgressDto = StudentProgressDto(
        id = domain.id,
        studentId = studentMapper.mapToDto(domain.studentId),
        totalInterviews = domain.totalInterviews,
        averageScore = domain.averageScore,
        uploadedCl = domain.uploadedCl,
        lastActivity = domain.lastActivity
    )
}