package com.frontend.oportunia.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.Company
import com.frontend.oportunia.domain.model.CompanyReview
import com.frontend.oportunia.domain.repository.CompanyRepository
import com.frontend.oportunia.domain.repository.CompanyReviewRepository
import com.frontend.oportunia.domain.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val repository: CompanyRepository,
    private val studentRepository: StudentRepository,
    private val reviewRepository: CompanyReviewRepository
) : ViewModel() {


    private val _companyList = MutableStateFlow<List<Company>>(emptyList())
    val companyList: StateFlow<List<Company>> = _companyList

    private val _selectedCompany = MutableStateFlow<Company?>(null)
    val selectedCompany: StateFlow<Company?> = _selectedCompany

    private val _companyReviewsList = MutableStateFlow<List<CompanyReview>>(emptyList())
    val companyReviewsList: StateFlow<List<CompanyReview>> = _companyReviewsList

    private val _studentCache = mutableMapOf<Long, String?>()
    private val _studentName = MutableStateFlow<String?>(null) // Almacenamos el nombre del estudiante
    val studentName: StateFlow<String?> = _studentName // Exponemos el estado

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

    fun getReviewsForCompany(companyId: Long) {
        viewModelScope.launch {
            reviewRepository.findCompanyReviewsByCompanyId(companyId)
                .onSuccess { _companyReviewsList.value = it }
                .onFailure { Log.e("CompanyViewModel", "Error searching for company with name: $companyId", it) }
        }
    }

    fun getStudentName(studentId: Long) {
        // Verificar si el nombre ya está en caché
        _studentCache[studentId]?.let {
            _studentName.value = it // Si está en caché, actualizamos el estado
            return
        }

        // Si no está en caché, hacer la solicitud y almacenar el resultado
        viewModelScope.launch {
            val result = studentRepository.findStudentById(studentId)
            if (result.isSuccess) {
                val student = result.getOrNull()
                val name = student?.user?.firstName?.plus(" ")?.plus(student?.user?.lastName)
                _studentCache[studentId] = name // Almacenar en caché
                _studentName.value = name // Actualizar el estado con el nombre
            } else {
                _studentCache[studentId] = null // Si falla, almacenar null en caché
                _studentName.value = null // Actualizar el estado con null
            }
        }
    }



}