package com.frontend.oportunia.data.remote.dto

import com.frontend.oportunia.domain.model.Choice

data class InterviewChatResponseDto(
    val choices: List<ChoiceDto>?,
    val interviewId: Long? = null
)
