package com.frontend.oportunia.presentation.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.User
import com.frontend.oportunia.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers: StateFlow<List<User>> get() = _allUsers

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val filteredUsers: StateFlow<List<User>> = combine(_allUsers, _searchQuery) { users, query ->
        if (query.isBlank()) {
            users
        } else {
            users.filter {
                it.firstName.contains(query, ignoreCase = true) ||
                        it.lastName.contains(query, ignoreCase = true) ||
                        it.email?.contains(query, ignoreCase = true) == true
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            userRepository.findAllUsers()
                .onSuccess { users ->
                    _allUsers.value = users
                }
                .onFailure { e ->
                    Log.e("UserViewModel", "Error cargando usuarios", e)
                    _errorMessage.value = "No se pudieron cargar los usuarios."
                }
        }
    }

    fun searchUsers(query: String) {
        _searchQuery.value = query
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            try {
                userRepository.deleteUser(user.id!!)
                    .onSuccess {
                        _allUsers.value = _allUsers.value.filterNot { it.id == user.id }
                    }
                    .onFailure {
                        _errorMessage.value = "Error al eliminar el usuario en el servidor."
                        Log.e("UserViewModel", "Error backend al eliminar", it)
                    }
            } catch (e: Exception) {
                _errorMessage.value = "Error inesperado al eliminar."
                Log.e("UserViewModel", "Error inesperado", e)
            }
        }
    }
}