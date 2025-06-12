package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.ChatResponseDto
import com.frontend.oportunia.data.remote.dto.ChoiceDto
import com.frontend.oportunia.data.remote.dto.InterviewChatResponseDto
import com.frontend.oportunia.data.remote.dto.InterviewDto
import com.frontend.oportunia.data.remote.dto.MessageDto
import com.frontend.oportunia.domain.model.ChatResponse
import com.frontend.oportunia.domain.model.Choice
import com.frontend.oportunia.domain.model.Interview
import com.frontend.oportunia.domain.model.InterviewChatResponse
import com.frontend.oportunia.domain.model.Message
import javax.inject.Inject

class InterviewMapper @Inject constructor (
    private val studentMapper: StudentMapper
) {
    fun mapToDomain(dto: InterviewDto): Interview = Interview(
        id = dto.id,
        date = dto.date,
        result = dto.result,
        jobPosition = dto.jobPosition,
        type = dto.type,
        student = studentMapper.mapToDomain(dto.student),
    )

    fun mapToDto(domain: Interview): InterviewDto = InterviewDto(
        id = domain.id,
        date = domain.date,
        result = domain.result,
        jobPosition = domain.jobPosition,
        type = domain.type,
        student = studentMapper.mapToDto(domain.student),

    )

    fun mapToDomain(dto: MessageDto): Message {
        return Message(
            role = dto.role,
            content = dto.content
        )
    }

    fun mapToDomain(dto: ChoiceDto): Choice {
        return Choice(
            message = mapToDomain(dto.message)
        )
    }

    fun mapToDomain(dto: ChatResponseDto): ChatResponse {
        return ChatResponse(
            choices = dto.choices.map { mapToDomain(it) }
        )
    }

    fun mapToDomain(dto: InterviewChatResponseDto): InterviewChatResponse {
        return InterviewChatResponse(
            choices = dto.choices?.map { mapToDomain(it) } ?: emptyList(),
            interviewId = dto.interviewId
        )
    }

}