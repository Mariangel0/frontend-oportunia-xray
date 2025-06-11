package com.frontend.oportunia.data.remote.serializer
import com.frontend.oportunia.data.remote.dto.InterviewDto
import com.frontend.oportunia.data.remote.dto.StudentDto
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.Date

class InterviewDeserializer : JsonDeserializer<InterviewDto> {
    override fun deserialize(
        json: JsonElement, typeOfT:
        Type,
        context: JsonDeserializationContext
    ): InterviewDto {
        val jsonObject = json.asJsonObject

        val id = jsonObject.get("id").asLong
        val studentId = context.deserialize<StudentDto>(jsonObject.get("studentId"), StudentDto::class.java)

        val date = jsonObject.get("date")?.let {
            context.deserialize<Date>(it, Date::class.java)
        } ?: Date()

        val result = jsonObject.get("result").asString

        return InterviewDto(id, studentId, date.toString(), result)

    }
}
