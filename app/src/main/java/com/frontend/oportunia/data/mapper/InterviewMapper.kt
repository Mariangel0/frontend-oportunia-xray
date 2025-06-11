package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.ChatResponseDto
import com.frontend.oportunia.data.remote.dto.ChoiceDto
import com.frontend.oportunia.data.remote.dto.InterviewDto
import com.frontend.oportunia.data.remote.dto.MessageDto
import com.frontend.oportunia.domain.model.ChatResponse
import com.frontend.oportunia.domain.model.Choice
import com.frontend.oportunia.domain.model.Interview
import com.frontend.oportunia.domain.model.Message
import javax.inject.Inject

class InterviewMapper @Inject constructor (
    private val studentMapper: StudentMapper
) {
    fun mapToDomain(dto: InterviewDto): Interview = Interview(
        id = dto.id,
        studentId = studentMapper.mapToDomain(dto.studentId),
        date = dto.date,
        result = dto.result
    )

    fun mapToDto(domain: Interview): InterviewDto = InterviewDto(
        id = domain.id,
        studentId = studentMapper.mapToDto(domain.studentId),
        date = domain.date,
        result = domain.result
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

}