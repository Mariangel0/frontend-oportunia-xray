package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.quiz.MCAnswerDto
import com.frontend.oportunia.data.remote.dto.quiz.MCEvaluationDto
import com.frontend.oportunia.data.remote.dto.quiz.MCQuestionDto
import com.frontend.oportunia.data.remote.dto.quiz.MCRequestDto
import com.frontend.oportunia.domain.model.*
import javax.inject.Inject

class QuizMapper @Inject constructor() {

    fun mapToDomain(dto: MCQuestionDto): MCQuestion = MCQuestion(
        question = dto.question,
        options = dto.options,
        correctAnswer = dto.correctAnswer
    )

    fun mapToDto(domain: MCQuestion): MCQuestionDto = MCQuestionDto(
        question = domain.question,
        options = domain.options,
        correctAnswer = domain.correctAnswer
    )

    fun mapToDomain(dto: MCEvaluationDto): MCEvaluation = MCEvaluation(
        question = dto.question,
        selectedOption = dto.selectedOption,
        correctAnswer = dto.correctAnswer,
        isCorrect = dto.isCorrect
    )

    fun mapToDto(domain: MCEvaluation): MCEvaluationDto = MCEvaluationDto(
        question = domain.question,
        selectedOption = domain.selectedOption,
        correctAnswer = domain.correctAnswer,
        isCorrect = domain.isCorrect
    )

    fun mapToDto(domain: MCRequest): MCRequestDto = MCRequestDto(
        topic = domain.topic,
        difficulty = domain.difficulty
    )

    fun mapToDto(domain: MCAnswer): MCAnswerDto = MCAnswerDto(
        selectedOption = domain.selectedOption
    )
}
