package com.frontend.oportunia.data.remote.dto

data class UserTextPromptDto(
    val id: Long,
    val message: String,
    val jobPosition: String,
    val typeOfInterview: String
)

