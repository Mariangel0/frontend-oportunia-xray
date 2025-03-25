package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.CurriculumDto

class CurriculumMapper {
    fun mapToDomain(dto: CurriculumDto): Curriculum = Curriculum(
        id = dto.id,
        studentId = dto.studentId,
        archiveUrl = dto.archiveUrl,
        feedback = dto.feedback
    )

    fun mapToDto(domain: Curriculum): CurriculumDto = CurriculumDto(
        id = domain.id,
        studentId = domain.studentId,
        archiveUrl = domain.archiveUrl,
        feedback = domain.feedback
    )
}