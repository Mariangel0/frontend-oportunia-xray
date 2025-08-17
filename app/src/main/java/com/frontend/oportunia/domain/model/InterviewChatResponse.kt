package com.frontend.oportunia.domain.model

data class InterviewChatResponse (
    val choices: List<Choice>?,
    val interviewId: Long? = null
)