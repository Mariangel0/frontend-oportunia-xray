package com.frontend.oportunia.data.remote.serializer

import com.frontend.oportunia.data.remote.dto.AbilityDto
import com.frontend.oportunia.data.remote.dto.StudentDto
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class AbilityDeserializer : JsonDeserializer<AbilityDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): AbilityDto {
        val jsonObject = json.asJsonObject

        val id = jsonObject.get("id").asLong

        // Antes hacías jsonObject.get("studentId"), pero en realidad tu API envía "student"
        val studentJsonElement = jsonObject.get("student")
            ?: throw JsonParseException("Falta el objeto \"student\" en la respuesta.")

        // Deserializamos el objeto “student” directamente a StudentDto // se podria el deserializador de student
        val studentDto = context.deserialize<StudentDto>(
            studentJsonElement,
            StudentDto::class.java
        )

        val name = jsonObject.get("name").asString
        return AbilityDto(id, studentDto, name)
    }
}
