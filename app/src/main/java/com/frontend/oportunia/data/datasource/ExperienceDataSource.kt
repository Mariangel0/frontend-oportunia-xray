package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.dto.ExperienceDto
import kotlinx.coroutines.flow.Flow

interface ExperienceDataSource {
    suspend fun getExperiences(): Flow<List<ExperienceDto>>
    suspend fun getExperienceById(id: Long): ExperienceDto?
    suspend fun insertExperience(experienceDto: ExperienceDto)
    suspend fun updateExperience(experienceDto: ExperienceDto)
    suspend fun deleteExperience(experienceDto: ExperienceDto)
}