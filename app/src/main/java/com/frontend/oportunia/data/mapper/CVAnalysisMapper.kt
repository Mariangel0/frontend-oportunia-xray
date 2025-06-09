package com.frontend.oportunia.data.mapper
import com.frontend.oportunia.data.remote.dto.AnalyzedCVResponseDto
import com.frontend.oportunia.domain.model.AnalyzedCVResponse
import javax.inject.Inject

class CVAnalysisMapper @Inject constructor() {

    fun mapToDomain(dto: AnalyzedCVResponseDto): AnalyzedCVResponse = AnalyzedCVResponse(
        recomendaciones = dto.recomendaciones,
        comentarios = dto.comentarios,
        score = dto.score
    )

    fun mapToDto(domain: AnalyzedCVResponse): AnalyzedCVResponseDto = AnalyzedCVResponseDto(
        recomendaciones = domain.recomendaciones,
        comentarios = domain.comentarios,
        score = domain.score
    )
}