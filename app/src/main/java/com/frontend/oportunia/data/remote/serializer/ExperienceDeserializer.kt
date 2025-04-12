package com.frontend.oportunia.data.remote.serializer

import com.frontend.oportunia.data.remote.dto.CompanyDto
import com.frontend.oportunia.data.remote.dto.ExperienceDto
import com.frontend.oportunia.data.remote.dto.StudentDto
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ExperienceDeserializer : JsonDeserializer<ExperienceDto> {
    override fun deserialize(json: JsonElement,
                             typeOfT: Type,
                             context: JsonDeserializationContext
    ): ExperienceDto {
        val jsonObject = json.asJsonObject

        val id = jsonObject.get("id").asLong
        val studentId = context.deserialize<StudentDto>(jsonObject.get("studentId"), StudentDto::class.java)
        val company = context.deserialize<CompanyDto>(jsonObject.get("company"), CompanyDto::class.java)
        val role = jsonObject.get("role").asString
        val year = jsonObject.get("year").asInt

        return ExperienceDto(id, studentId, company, role, year)
    }
}