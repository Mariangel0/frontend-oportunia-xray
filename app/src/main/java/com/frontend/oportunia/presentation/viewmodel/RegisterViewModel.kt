package com.frontend.oportunia.presentation.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.Education
import com.frontend.oportunia.domain.model.Experience
import com.frontend.oportunia.domain.model.Privilege
import com.frontend.oportunia.domain.model.Role
import com.frontend.oportunia.domain.model.Student
import com.frontend.oportunia.domain.model.User
import com.frontend.oportunia.domain.repository.EducationRepository
import com.frontend.oportunia.domain.repository.ExperienceRepository
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
class RegisterViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val userRepository: UserRepository,
    private val educationRepository: EducationRepository,
    private val experienceRepository: ExperienceRepository
) : ViewModel() {

    /* ───────────── DATOS BÁSICOS ───────────── */
    val name            = MutableStateFlow("")
    val lastName        = MutableStateFlow("")
    val email           = MutableStateFlow("")
    val password        = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")
    val birthDate       = MutableStateFlow<Long?>(null)

    /* ───────────── ESTADO UI ───────────── */
    val selectedImageUri = MutableStateFlow<Uri?>(null)
    val isWorking        = MutableStateFlow(false)
    val company          = MutableStateFlow("")
    val jobPosition      = MutableStateFlow("")
    val githubUrl        = MutableStateFlow("")
    val linkedinUrl      = MutableStateFlow("")

    /* ───────────── ERRORES ───────────── */
    private val _error           = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error
    val showErrorDialog          = MutableStateFlow(false)

    /* ───────────── COMPATIBILIDAD ANTIGUA ─────────────
       (si otras pantallas usan aún este flujo)            */
    val education = MutableStateFlow<List<String>>(emptyList())

    /* ───────────── LISTAS OBSERVABLES ───────────── */
    data class EducationEntry(
        val id: Long = System.nanoTime(),
        val degree: String = "",
        val institution: String = "",
        val graduationYear: String = ""
    )

    data class ExperienceEntry(
        val id: Long = System.nanoTime(),
        val company: String = "",
        val role: String = "",
        val timeline: String = ""
    )

    val educationList : SnapshotStateList<EducationEntry>  = mutableStateListOf()
    val experienceList: SnapshotStateList<ExperienceEntry> = mutableStateListOf()

    /* ───────────── EDUCACIÓN ───────────── */
    fun addEducationEntry() {
        educationList.add(EducationEntry())
    }

    fun updateEducationEntry(index: Int, field: String, value: String) {
        if (index !in educationList.indices) return
        val entry = educationList[index]
        educationList[index] = when (field) {
            "degree"         -> entry.copy(degree = value)
            "institution"    -> entry.copy(institution = value)
            "graduationYear" -> entry.copy(graduationYear = value)
            else             -> entry
        }
    }

    fun removeEducationEntry(index: Int) {
        if (index in educationList.indices) educationList.removeAt(index)
    }



    /* ───────────── EXPERIENCIA ───────────── */
    fun addExperienceEntry() {
        experienceList.add(ExperienceEntry())
    }

    fun updateExperienceEntry(index: Int, field: String, value: String) {
        if (index !in experienceList.indices) return
        val entry = experienceList[index]
        experienceList[index] = when (field) {
            "company"  -> entry.copy(company = value)
            "role"     -> entry.copy(role     = value)
            "timeline" -> entry.copy(timeline = value)
            else       -> entry
        }
    }



    fun removeExperienceEntry(index: Int) {
        if (index in experienceList.indices) experienceList.removeAt(index)
    }

    /* ───────────── UTILIDADES ───────────── */
    fun onImageSelected(uri: Uri?) {
        selectedImageUri.value = uri
    }

    private fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }


    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }



    fun registerStudent(onSuccess: () -> Unit) {
        if (name.value.isBlank() || lastName.value.isBlank() || email.value.isBlank() || password.value.isBlank() || confirmPassword.value.isBlank() || birthDate.value == null  ) {
            _error.value = "empty_fields"
            showErrorDialog.value = true

            return
        }

        if (password.value != confirmPassword.value) {
            _error.value = "password_mismatch"
            showErrorDialog.value = true

            return
        }

        if (!isValidEmail(email.value)) {
            _error.value = "invalid_email"
            showErrorDialog.value = true

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
                if (result.isSuccess) {
                    _error.value = null
                    student.userId = result.getOrNull()?.id
                    val result = studentRepository.createStudent(student)
                    if (result.isSuccess) {
                        _error.value = null

                        println(result.getOrNull())
                        educationList.forEach { edu ->
                            println("Enviando educación → degree=${edu.degree}, institution=${edu.institution}, year=${edu.graduationYear}")
                            educationRepository.createEducation(

                                Education(
                                    student = result.getOrNull()?: return@launch,
                                    name = edu.degree,
                                    institution = edu.institution,
                                    year = edu.graduationYear.toInt()
                                )
                            )
                        }

                        experienceList.forEach { exp ->
                            experienceRepository.createExperience(
                                Experience(
                                    student = result.getOrNull() ?: return@launch,
                                    company = exp.company,
                                    role = exp.role,
                                    timeline = exp.timeline
                                )
                            )
                        }

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
