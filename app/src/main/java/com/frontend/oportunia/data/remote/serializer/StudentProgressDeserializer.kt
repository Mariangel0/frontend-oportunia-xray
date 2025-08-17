package com.frontend.oportunia.data.remote.serializer


import com.frontend.oportunia.data.remote.dto.StudentDto
import com.frontend.oportunia.data.remote.dto.StudentProgressDto
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.Date


class StudentProgressDeserializer : JsonDeserializer<StudentProgressDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): StudentProgressDto {
        val jsonObject = json.asJsonObject

        val id = jsonObject.get("id").asLong
        val studentId = context.deserialize<StudentDto>(jsonObject.get("studentId"), StudentDto::class.java)

        val totalInterviews = jsonObject.get("totalInterviews").asInt
        val averageScore = jsonObject.get("averageScore").asFloat
        val uploadedCl = jsonObject.get("uploadedCl").asInt

        val lastActivity = jsonObject.get("lastActivity")?.let {
            context.deserialize<Date>(it, Date::class.java)
        } ?: Date()

        return StudentProgressDto(id, studentId, totalInterviews, averageScore, uploadedCl, lastActivity.toString())
    }
}
