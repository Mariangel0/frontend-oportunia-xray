package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.dto.EducationDto
import kotlinx.coroutines.flow.Flow

interface EducationDataSource {
    suspend fun getEducations(): Flow<List<EducationDto>>
    suspend fun getEducationById(id: Long): EducationDto?
    suspend fun insertEducation(educationDto: EducationDto)
    suspend fun updateEducation(educationDto: EducationDto)
    suspend fun deleteEducation(educationDto: EducationDto)
}