package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.Experience

interface ExperienceRepository {
    suspend fun findExperienceById(experienceId: Long): Result<Experience>
    suspend fun findAllExperiences(): Result<List<Experience>>
}