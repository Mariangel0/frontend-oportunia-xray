package com.frontend.oportunia.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oportunia.R
import com.frontend.oportunia.domain.model.ChatMessage
import com.frontend.oportunia.domain.model.InterviewChatResponse
import com.frontend.oportunia.domain.model.User
import com.frontend.oportunia.domain.repository.AuthRepository
import com.frontend.oportunia.domain.repository.InterviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class InterviewViewModel @Inject constructor(
    private val interviewRepository: InterviewRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    init {
        getUser()
    }

    private val _loggedUser = MutableStateFlow<User?>(null)
    val loggedUser: StateFlow<User?> get() = _loggedUser

    private val _messages = mutableStateListOf<ChatMessage>()
    val messages: List<ChatMessage> get() = _messages

    var isLoading by mutableStateOf(false)
        private set

    private val _interviewFinished = MutableStateFlow(false)
    val interviewFinished: StateFlow<Boolean> get() = _interviewFinished

    private val _finalInterviewId = MutableStateFlow<Long?>(null)
    val finalInterviewId: StateFlow<Long?> get() = _finalInterviewId

    private var isFirstMessage = true

    var currentJobPosition: String = ""
    var currentInterviewType: String = ""

    fun resetChat() {
        _messages.clear()
        isFirstMessage = true
        currentJobPosition = ""
        currentInterviewType = ""
        _interviewFinished.value = false
        _finalInterviewId.value = null
    }

    fun startInterview(
        context: Context,
        studentId: Long,
        jobPosition: String,
        interviewType: String,
        initialMessage: String
    ) {
        resetChat()
        currentJobPosition = jobPosition
        currentInterviewType = interviewType
        sendMessage(context, studentId, initialMessage)
    }

    fun sendMessage(
        context: Context,
        studentId: Long,
        message: String
    ) {
        if (!isFirstMessage) {
            _messages.add(ChatMessage(UUID.randomUUID().toString(), message, true))
        }
        isLoading = true

        viewModelScope.launch {
            val result = if (isFirstMessage) {
                isFirstMessage = false
                interviewRepository.startInterview(
                    studentId = studentId,
                    message = message,
                    jobPosition = currentJobPosition,
                    typeOfInterview = currentInterviewType
                )
            } else {
                interviewRepository.continueInterview(
                    studentId = studentId,
                    message = message
                )
            }

            result.onSuccess { response: InterviewChatResponse ->
                val reply = response.choices?.firstOrNull()?.message?.content
                    ?: context.getString(R.string.no_model_response)

                _messages.add(ChatMessage(UUID.randomUUID().toString(), reply, isFromUser = false))

                // ✅ Navegar justo después de agregar el mensaje final
                if (reply.contains("esta entrevista ha concluido", ignoreCase = true)) {
                    _interviewFinished.value = true
                    _finalInterviewId.value = response.interviewId
                }

            }.onFailure { e ->
                _messages.add(
                    ChatMessage(
                        UUID.randomUUID().toString(),
                        context.getString(R.string.network_error_template, e.message ?: "Error desconocido"),
                        isFromUser = false
                    )
                )
            }

            isLoading = false
        }
    }

    fun getUser() {
        viewModelScope.launch {
            authRepository.getCurrentUser()
                .onSuccess { _loggedUser.value = it }
                .onFailure { Log.e("AuthViewModel", "Error searching for user", it) }
        }
    }
}
