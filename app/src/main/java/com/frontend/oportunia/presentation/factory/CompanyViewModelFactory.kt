package com.frontend.oportunia.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frontend.oportunia.domain.repository.CompanyRepository
import com.frontend.oportunia.presentation.viewmodel.CompanyViewModel

class CompanyViewModelFactory(
    private val companyRepository: CompanyRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompanyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CompanyViewModel(companyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}