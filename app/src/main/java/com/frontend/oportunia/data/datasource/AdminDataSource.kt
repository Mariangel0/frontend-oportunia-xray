package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.dto.AdminDto
import kotlinx.coroutines.flow.Flow

interface AdminDataSource {
    suspend fun getAdmins(): Flow<List<AdminDto>>
    suspend fun getAdminById(id: Long): AdminDto?
    suspend fun insertAdmin(adminDto: AdminDto)
    suspend fun updateAdmin(adminDto: AdminDto)
    suspend fun deleteAdmin(adminDto: AdminDto)
}