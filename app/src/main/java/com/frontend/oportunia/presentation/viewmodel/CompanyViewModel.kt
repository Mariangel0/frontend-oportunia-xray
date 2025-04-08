package com.frontend.oportunia.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.Company
import com.frontend.oportunia.domain.repository.CompanyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val repository: CompanyRepository
) : ViewModel() {


    private val _companyList = MutableStateFlow<List<Company>>(emptyList())
    val companyList: StateFlow<List<Company>> = _companyList

    private val _selectedCompany = MutableStateFlow<Company?>(null)
    val selectedCompany: StateFlow<Company?> = _selectedCompany

    fun loadAllCompanies() {
        viewModelScope.launch {
            repository.findAllCompanies()
                .onSuccess { _companyList.value = it }
                .onFailure { Log.e("CompanyViewModel", "Error loading companies", it) }
        }
    }

    fun loadCompanyById(id: Long) {
        viewModelScope.launch {
            repository.findCompanyById(id)
                .onSuccess { _selectedCompany.value = it }
                .onFailure { Log.e("CompanyViewModel", "Error loading company with id: $id", it) }
        }
    }
    fun loadCompanyByName(name: String) {
        viewModelScope.launch {
            repository.findCompanyByName(name)
                .onSuccess { _companyList.value = it }
                .onFailure { Log.e("CompanyViewModel", "Error searching for company with name: $name", it) }
        }
    }

}