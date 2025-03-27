package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.datasource.model.StudentProgressDto
import com.frontend.oportunia.data.mapper.StudentProgressMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StudentProgressDataSourceImpl()
//    private val studentProgresMapper : StudentProgressMapper
//) : StudentProgressDataSource {
//
//    override suspend fun getStudentProgress(): Flow<List<StudentProgressDto>> = flow {
//        val studentProgress = StudentProgressProvider.findAllStudentProgress()
//        emit(studentProgress.map{ studentProgresMapper.mapToDto(it)})
//    }
//
//    override suspend fun getStudentProgressById(id: Long): StudentProgressDto? {
//        StudentProgressProvider.findStudentById(id)?.let {
//            studentProgresMapper.mapToDto(it) }
//    }
//
//    override suspend fun insertStudentProgress(studentProgressDto: StudentProgressDto) {
//    }
//
//    override suspend fun updateStudentProgress(studentProgressDto: StudentProgressDto) {
//    }
//
//    override suspend fun deleteStudentProgress(studentProgressDto: StudentProgressDto) {
//    }
//}
