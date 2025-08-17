package com.frontend.oportunia.data.remote.dto

data class InterviewDto(
    val id: Long,
    val date: String,
    val result: String,
    var jobPosition: String,
    var type: String,
    val student: StudentDto,
)

