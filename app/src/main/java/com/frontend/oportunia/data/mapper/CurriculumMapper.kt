package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.CurriculumDto

class CurriculumMapper(
    private val studentMapper: StudentMapper
) {
    fun mapToDomain(dto: CurriculumDto): Curriculum = Curriculum(
        id = dto.id,
        studentId = studentMapper.mapToDomain(dto.studentId),
        archiveUrl = dto.archiveUrl,
        feedback = dto.feedback
    )

    fun mapToDto(domain: Curriculum): CurriculumDto = CurriculumDto(
        id = domain.id,
        studentId = studentMapper.mapToDto(domain.studentId),
        archiveUrl = domain.archiveUrl,
        feedback = domain.feedback
    )
}