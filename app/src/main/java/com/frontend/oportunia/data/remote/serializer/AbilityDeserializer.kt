package com.frontend.oportunia.data.remote.serializer


import com.frontend.oportunia.data.remote.dto.AbilityDto
import com.frontend.oportunia.data.remote.dto.StudentDto
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class AbilityDeserializer : JsonDeserializer<AbilityDto> {
    override fun deserialize(json: JsonElement,
                             typeOfT: Type,
                             context: JsonDeserializationContext
    ): AbilityDto {
        val jsonObject = json.asJsonObject

        val id = jsonObject.get("id").asLong
        val studentId = context.deserialize<StudentDto>(jsonObject.get("studentId"), StudentDto::class.java)
        val name = jsonObject.get("name").asString

        return AbilityDto(id, studentId, name)
    }
}