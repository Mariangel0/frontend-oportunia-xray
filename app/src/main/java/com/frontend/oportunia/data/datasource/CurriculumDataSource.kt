package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.dto.CurriculumDto
import kotlinx.coroutines.flow.Flow

interface CurriculumDataSource {
    suspend fun getCurriculums(): Flow<List<CurriculumDto>>
    suspend fun getCurriculumById(id: Long): CurriculumDto?
    suspend fun insertCurriculum(curriculumDto: CurriculumDto)
    suspend fun updateCurriculum(curriculumDto: CurriculumDto)
    suspend fun deleteCurriculum(curriculumDto: CurriculumDto)
}