package com.frontend.oportunia.data.remote.dto

data class ExperienceDto(
    val id: Long?,
    val timeline: String,
    val role: String,
    val student: StudentDto,
    val company: String,
)