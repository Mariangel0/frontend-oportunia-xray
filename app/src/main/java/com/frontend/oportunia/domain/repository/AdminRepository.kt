package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.Admin

interface AdminRepository {
    suspend fun findAllAdmins(): Result<List<Admin>>
    suspend fun findAdminById(adminId: Long): Result<Admin>
}