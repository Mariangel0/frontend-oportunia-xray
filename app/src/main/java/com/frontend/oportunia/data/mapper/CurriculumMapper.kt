package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.CurriculumDto
import com.frontend.oportunia.domain.model.Curriculum
import javax.inject.Inject

class CurriculumMapper  @Inject constructor(
    private val studentMapper: StudentMapper
) {
    fun mapToDomain(dto: CurriculumDto): Curriculum = Curriculum(
        id = dto.id,
        archiveUrl = dto.archiveUrl,
        s3Key = dto.s3Key,
        student = studentMapper.mapToDomain(dto.student),
    )

    fun mapToDto(domain: Curriculum): CurriculumDto = CurriculumDto(
        id = domain.id,
        student = studentMapper.mapToDto(domain.student),
        archiveUrl = domain.archiveUrl,
        s3Key = domain.s3Key
    )
}