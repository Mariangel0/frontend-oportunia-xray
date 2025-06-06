package com.frontend.oportunia.data.remote.serializer


import com.frontend.oportunia.data.remote.dto.CompanyDto
import com.frontend.oportunia.data.remote.dto.ExperienceDto
import com.frontend.oportunia.data.remote.dto.StudentDto
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class ExperienceDeserializer : JsonDeserializer<ExperienceDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ExperienceDto {
        val jsonObject = json.asJsonObject

        val id = jsonObject.get("id").asLong
        val role = jsonObject.get("role").asString

        val timeline = jsonObject.get("timeline").asString

        val studentDto = if (jsonObject.has("student") && !jsonObject.get("student").isJsonNull) {
            context.deserialize<StudentDto>(jsonObject.get("student"), StudentDto::class.java)
        } else {
            throw JsonParseException("Experience must contain non-null student")
        }

        val companyName = jsonObject.get("company").asString

        return ExperienceDto(
            id = id,
            timeline = timeline,
            role = role,
            studentId = studentDto,
            company = companyName
        )
    }
}

