package com.frontend.oportunia.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.Company
import com.frontend.oportunia.domain.model.CompanyReview
import com.frontend.oportunia.domain.repository.AuthRepository
import com.frontend.oportunia.domain.repository.CompanyRepository
import com.frontend.oportunia.domain.repository.CompanyReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// ✅ Estado del formulario de empresa
data class CompanyFormState(
    val name: String = "",
    val description: String = "",
    val rating: String = "",
    val type: String = "",
    val location: String = "",
    val employees: String = "",
    val websiteUrl: String = "",
    val vision: String = "",
    val mission: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val success: Boolean = false
)

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val repository: CompanyRepository,
    private val reviewRepository: CompanyReviewRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    // 🔄 Listado de empresas
    private val _companyList = MutableStateFlow<List<Company>>(emptyList())
    val companyList: StateFlow<List<Company>> = _companyList

    // 🔎 Empresa seleccionada
    private val _selectedCompany = MutableStateFlow<Company?>(null)
    val selectedCompany: StateFlow<Company?> = _selectedCompany

    // 💬 Reseñas de la empresa
    private val _companyReviewsList = MutableStateFlow<List<CompanyReview>>(emptyList())
    val companyReviewsList: StateFlow<List<CompanyReview>> = _companyReviewsList

    // 👤 Usuario autenticado
    private val _userName = MutableLiveData<String?>()
    val userName: LiveData<String?> = _userName

    // 🧾 Estado del formulario de creación
    private val _formState = MutableStateFlow(CompanyFormState())
    val formState: StateFlow<CompanyFormState> = _formState

    // 🟢 Cambiar campos del formulario
    fun onFieldChange(update: (CompanyFormState) -> CompanyFormState) {
        _formState.value = update(_formState.value)
    }

    // ✅ Crear empresa
    fun createCompany() {
        val state = _formState.value

        if (state.name.isBlank() || state.description.isBlank()) {
            _formState.value = state.copy(errorMessage = "Nombre y descripción son obligatorios.")
            return
        }

        val company = Company(
            id = null,
            name = state.name,
            description = state.description,
            rating = state.rating.toFloatOrNull() ?: 0f,
            type = state.type.ifBlank { null },
            location = state.location.ifBlank { null },
            employees = state.employees.toIntOrNull(),
            websiteUrl = state.websiteUrl.ifBlank { null },
            vision = state.vision.ifBlank { null },
            mission = state.mission.ifBlank { null }
        )

        viewModelScope.launch {
            _formState.value = state.copy(isLoading = true, errorMessage = null, success = false)

            repository.createCompany(company)
                .onSuccess {
                    _formState.value = _formState.value.copy(success = true)
                    loadAllCompanies() // opcional: refrescar lista
                }
                .onFailure {
                    Log.e("CompanyViewModel", "Error al crear empresa", it)
                    _formState.value = _formState.value.copy(errorMessage = it.localizedMessage ?: "Error desconocido")
                }

            _formState.value = _formState.value.copy(isLoading = false)
        }
    }

    fun resetForm() {
        _formState.value = CompanyFormState()
    }

    fun clearFormStatus() {
        _formState.value = _formState.value.copy(success = false, errorMessage = null)
    }

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
                .onFailure { Log.e("CompanyViewModel", "Error loading reviews for company: $companyId", it) }
        }
    }
}
