package com.frontend.oportunia.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.Privilege
import com.frontend.oportunia.domain.model.Role
import com.frontend.oportunia.domain.model.Student
import com.frontend.oportunia.domain.model.User
import com.frontend.oportunia.domain.repository.StudentRepository
import com.frontend.oportunia.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor (
    private val repository: StudentRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val name = MutableStateFlow("")
    val lastName = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")
    val birthDate = MutableStateFlow<Long?>(null)
    val selectedImageUri = MutableStateFlow<Uri?>(null)
    val education = MutableStateFlow<List<String>>(emptyList())
    val isWorking = MutableStateFlow(false)
    val company = MutableStateFlow("")
    val jobPosition = MutableStateFlow("")
    val linkedinUrl = MutableStateFlow("")
    val githubUrl = MutableStateFlow("")



    data class EducationEntry(
        var degree: String = "",
        var institution: String = "",
        var graduationYear: String = ""
    )

    data class ExperienceEntry(
        var company: String = "",
        var role: String = "",
        var timeline: String = ""
    )

    val educationList = MutableStateFlow<List<EducationEntry>>(emptyList())
    val experienceList = MutableStateFlow<List<ExperienceEntry>>(emptyList())

    fun addEducationEntry() {
        educationList.value = educationList.value + EducationEntry()
    }

    fun updateEducationEntry(index: Int, field: String, value: String) {
        val updated = educationList.value.toMutableList()
        val entry = updated[index]
        when (field) {
            "degree" -> entry.degree = value
            "institution" -> entry.institution = value
            "graduationYear" -> entry.graduationYear = value
        }
        educationList.value = updated
    }

    fun removeEducationEntry(index: Int) {
        educationList.value = educationList.value.toMutableList().apply { removeAt(index) }
    }

    fun addExperienceEntry() {
        experienceList.value = experienceList.value + ExperienceEntry()
    }

    fun updateExperienceEntry(index: Int, field: String, value: String) {
        val updated = experienceList.value.toMutableList()
        val entry = updated[index]
        when (field) {
            "company" -> entry.company = value
            "role" -> entry.role = value
            "timeline" -> entry.timeline = value
        }
        experienceList.value = updated
    }

    fun removeExperienceEntry(index: Int) {
        experienceList.value = experienceList.value.toMutableList().apply { removeAt(index) }
    }




    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun onImageSelected(uri: Uri?) {
        selectedImageUri.value = uri
    }

    fun clearError() {
        _error.value = null
    }

    private val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog: StateFlow<Boolean> get() = _showErrorDialog

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }



    fun registerStudent(onSuccess: () -> Unit) {
        if (name.value.isBlank() || lastName.value.isBlank() || email.value.isBlank() || password.value.isBlank() || confirmPassword.value.isBlank() || birthDate.value == null  ) {
            _error.value = "empty_fields"
            return
        }

        if (password.value != confirmPassword.value) {
            _error.value = "password_mismatch"
            return
        }

        if (!isValidEmail(email.value)) {
            _error.value = "invalid_email"
            _showErrorDialog.value = true
            return
        }

        val ln = linkedinUrl.value?.takeIf { it.isNotBlank() } ?: ""
        val gh = githubUrl.value?.takeIf { it.isNotBlank() } ?: ""




        val user = User(
            createDate = Date(),
            email = email.value,
            enabled = true,
            firstName = name.value,
            lastName = lastName.value,
            password = password.value,
            tokenExpired = false,
            roles = listOf(
                Role(
                    id = 1,
                    name = "USER",
                    privileges  = listOf(
                        Privilege(
                            id = 1,
                            name = "WRITE_PRIVILEGE"
                        )
                    )


                )
            )

        )
        val formattedDate = birthDate.value?.let { convertMillisToDate(it) } ?: ""
        val student = Student(
            user = user,
            description = "Nuevo estudiante",
            premium = false,
            linkedinUrl = ln,
            githubUrl = gh ,
            bornDate = formattedDate,
            location = "",

        )

        viewModelScope.launch {
            try {
                val result = userRepository.createUser(user)
                println("AQUI TODO BIEN")
                if (result.isSuccess) {
                    println("AQUI TODO BIEN 2")
                    _error.value = null
                    println("AQUI TODO BIEN 3")
                    student.userId = result.getOrNull()?.id
                    val result = repository.createStudent(student)
                    println("AQUI TODO BIEN 4")
                    if (result.isSuccess) {
                        _error.value = null
                        onSuccess()
                    } else {
                        _error.value = "register_failed"
                    }
                } else {
                    _error.value = "register_failed"

                }
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "network_error"
            }
        }
    }



}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}