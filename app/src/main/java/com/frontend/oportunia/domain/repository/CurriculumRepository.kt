package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.Curriculum

interface CurriculumRepository {
    suspend fun findAllCurriculums(): Result<List<Curriculum>>
    suspend fun findCurriculumById(curriculumId: Long): Result<Curriculum>
}