//package com.frontend.oportunia.presentation.viewmodel
//
//import com.frontend.oportunia.domain.repository.InterviewRepository
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.frontend.oportunia.presentation.ui.screens.ChatMessage
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import java.util.*
//import javax.inject.Inject
//
//@HiltViewModel
//class InterviewViewModel @Inject constructor(
//    private val interviewRepository: InterviewRepository
//) : ViewModel() {
//
//    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
//    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()
//
//    private val _isLoading = MutableStateFlow(false)
//    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
//
//    private val _error = MutableStateFlow<String?>(null)
//    val error: StateFlow<String?> = _error.asStateFlow()
//
//    private var currentJobPosition: String = ""
//    private var currentCompany: String = ""
//    private var currentInterviewType: String = ""
//
//    fun initializeInterview(jobPosition: String, companyName: String, interviewType: String) {
//        currentJobPosition = jobPosition
//        currentCompany = companyName
//        currentInterviewType = interviewType
//
//        // Send initial greeting from AI
//        val welcomeMessage = ChatMessage(
//            id = UUID.randomUUID().toString(),
//            content = "¡Hola! Soy tu asistente de entrevistas de OportunIA. Estamos simulando una entrevista para el puesto de $jobPosition en $companyName. Este será un proceso de entrevista $interviewType. ¿Estás listo para comenzar?",
//            isFromUser = false
//        )
//
//        _messages.value = listOf(welcomeMessage)
//    }
//
//    fun sendMessage(messageContent: String) {
//        // Add user message
//        val userMessage = ChatMessage(
//            id = UUID.randomUUID().toString(),
//            content = messageContent,
//            isFromUser = true
//        )
//
//        _messages.value = _messages.value + userMessage
//
//        // Get AI response
//        getAIResponse(messageContent)
//    }
//
//    private fun getAIResponse(userMessage: String) {
//        viewModelScope.launch {
//            try {
//                _isLoading.value = true
//                _error.value = null
//
//                // Call repository to get AI response
//                val response = interviewRepository.sendInterviewMessage(
//                    jobPosition = currentJobPosition,
//                    companyName = currentCompany,
//                    interviewType = currentInterviewType,
//                    message = userMessage,
//                    conversationHistory = _messages.value.map {
//                        mapOf(
//                            "role" to if (it.isFromUser) "user" else "assistant",
//                            "content" to it.content
//                        )
//                    }
//                )
//
//                val aiMessage = ChatMessage(
//                    id = UUID.randomUUID().toString(),
//                    content = response.message,
//                    isFromUser = false
//                )
//
//                _messages.value = _messages.value + aiMessage
//
//            } catch (e: Exception) {
//                _error.value = e.message ?: "Error desconocido"
//
//                // Add error message to chat
//                val errorMessage = ChatMessage(
//                    id = UUID.randomUUID().toString(),
//                    content = "Lo siento, hubo un error procesando tu mensaje. Por favor, intenta de nuevo.",
//                    isFromUser = false
//                )
//                _messages.value = _messages.value + errorMessage
//
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//
//    fun clearMessages() {
//        _messages.value = emptyList()
//    }
//
//    fun clearError() {
//        _error.value = null
//    }
//}