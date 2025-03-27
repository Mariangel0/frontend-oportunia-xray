package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.datasource.model.ExperienceDto
import com.frontend.oportunia.data.mapper.ExperienceMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExperienceDataSourceImpl()
//    private val experienceMapper: ExperienceMapper
//) : ExperienceDataSource {
//
//    override suspend fun getExperiences(): Flow<List<ExperienceDto>> = flow {
//        val experiences = ExperienceProvider.findAllExperiences()  // Simula la obtención de experiencias
//        emit(experiences.map { experienceMapper.mapToDto(it) })
//    }
//
//    override suspend fun getExperienceById(id: Long): ExperienceDto? = ExperienceProvider.findExperienceById(id)?.let {
//        experienceMapper.mapToDto(it)
//    }
//
//    override suspend fun insertExperience(experienceDto: ExperienceDto) {
//    }
//
//    override suspend fun updateExperience(experienceDto: ExperienceDto) {
//    }
//
//    override suspend fun deleteExperience(experienceDto: ExperienceDto) {
//    }
//}
