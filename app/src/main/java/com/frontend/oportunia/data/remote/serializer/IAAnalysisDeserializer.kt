package com.frontend.oportunia.data.remote.serializer

import com.frontend.oportunia.data.remote.dto.IAAnalysisDto
import com.frontend.oportunia.data.remote.dto.InterviewDto
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.Date

class IAAnalysisDeserializer : JsonDeserializer<IAAnalysisDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): IAAnalysisDto {
        val jsonObject = json.asJsonObject

        val id = jsonObject.get("id").asLong
        val interviewId = context.deserialize<InterviewDto>(jsonObject.get("interview"),
            InterviewDto::class.java)

        val recommendations = jsonObject.get("recommendation").asString
        val comment = jsonObject.get("comment").asString
        val score = jsonObject.get("score").asFloat

        val date = jsonObject.get("date")?.let {
            context.deserialize<Date>(it, Date::class.java)
        } ?: Date()

        return IAAnalysisDto(id, recommendations, comment, score, date.toString(), interviewId)
    }
}
