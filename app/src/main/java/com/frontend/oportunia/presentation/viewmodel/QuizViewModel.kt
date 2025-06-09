package com.frontend.oportunia.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.MCAnswer
import com.frontend.oportunia.domain.model.MCEvaluation
import com.frontend.oportunia.domain.model.MCQuestion
import com.frontend.oportunia.domain.model.MCRequest
import com.frontend.oportunia.domain.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _questions = MutableStateFlow<List<MCQuestion>>(emptyList())
    val questions: StateFlow<List<MCQuestion>> = _questions

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    private val _evaluations = MutableStateFlow<Map<String, MCEvaluation>>(emptyMap())
    val evaluations: StateFlow<Map<String, MCEvaluation>> = _evaluations

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var topic: String? = null
    private var difficulty: String? = null

    fun initializeQuiz(userId: Long, topic: String, difficulty: String) {
        this.topic = topic
        this.difficulty = difficulty
        _questions.value = emptyList()
        _evaluations.value = emptyMap()
        _currentIndex.value = 0
        fetchNextQuestion(userId)
    }

    private fun fetchNextQuestion(userId: Long) {
        val currentTopic = topic ?: return
        val currentDifficulty = difficulty ?: return

        viewModelScope.launch {
            _isLoading.value = true
            quizRepository.generateQuiz(userId, MCRequest(currentTopic, currentDifficulty)).onSuccess { question ->
                _questions.value = _questions.value + question
            }.onFailure {
                Log.e("QuizViewModel", "Error generating next question", it)
            }
            _isLoading.value = false
        }
    }

    fun answerQuestion(userId: Long, selectedOption: String) {
        viewModelScope.launch {
            val question = _questions.value.getOrNull(_currentIndex.value) ?: return@launch
            quizRepository.evaluateAnswer(userId, MCAnswer(selectedOption)).onSuccess { eval ->
                _evaluations.value = _evaluations.value.toMutableMap().apply {
                    put(question.question, eval)
                }
            }.onFailure {
                Log.e("QuizViewModel", "Error evaluating answer", it)
            }
        }
    }

    fun nextQuestion(userId: Long) {
        if (_questions.value.size < 5 && _currentIndex.value == _questions.value.lastIndex) {
            fetchNextQuestion(userId)
        }
        if (_currentIndex.value < _questions.value.lastIndex) {
            _currentIndex.value += 1
        }
    }

    fun resetQuiz() {
        _questions.value = emptyList()
        _evaluations.value = emptyMap()
        _currentIndex.value = 0
        topic = null
        difficulty = null
    }

    fun previousQuestion() {
        if (_currentIndex.value > 0) {
            _currentIndex.value -= 1
        }
    }

    fun goToQuestion(index: Int) {
        if (index >= 0 && index < questions.value.size) {
            _currentIndex.value = index
        }
    }
}
