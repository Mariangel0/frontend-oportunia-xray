package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.Education

interface EducationRepository {
    suspend fun findEducationById(educationId: Long): Result<Education>
    suspend fun findAllEducations(): Result<List<Education>>
}