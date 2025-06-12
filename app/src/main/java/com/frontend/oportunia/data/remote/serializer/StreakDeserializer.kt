package com.frontend.oportunia.data.remote.serializer

import com.frontend.oportunia.data.remote.dto.StreakDto
import com.frontend.oportunia.data.remote.dto.StudentDto
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.Date

class StreakDeserializer : JsonDeserializer<StreakDto> {
    override fun deserialize(
        json: JsonElement,
        ypeOfT: Type,
        context: JsonDeserializationContext
    ): StreakDto {
        val jsonObject = json.asJsonObject

        val id = jsonObject.get("id").asLong
        val studentId = context.deserialize<StudentDto>(jsonObject.get("studentId"), StudentDto::class.java)

        val lastActivity = jsonObject.get("lastActivity")?.let {
            context.deserialize<Date>(it, Date::class.java)
        } ?: Date()

        val days = jsonObject.get("days").asInt
        val bestStreak = jsonObject.get("bestStreak").asInt

        return StreakDto(id, studentId, days, lastActivity, bestStreak)
    }
}
