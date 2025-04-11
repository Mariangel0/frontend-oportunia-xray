package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.dto.EducationDto
import com.frontend.oportunia.data.mapper.EducationMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//class EducationDataSourceImpl(
//    private val educationMapper: EducationMapper
//) : EducationDataSource {
//
//    override suspend fun getEducations(): Flow<List<EducationDto>> = flow {
//        val educations = EducationProvider.findAllEducations()  // Simula la obtención de educación
//        emit(educations.map { educationMapper.mapToDto(it) })
//    }
//
//    override suspend fun getEducationById(id: Long): EducationDto? = EducationProvider.findEducationById(id)?.let {
//        educationMapper.mapToDto(it)
//    }
//
//    override suspend fun insertEducation(educationDto: EducationDto) {
//    }
//
//    override suspend fun updateEducation(educationDto: EducationDto) {
//    }
//
//    override suspend fun deleteEducation(educationDto: EducationDto) {
//    }
//}
