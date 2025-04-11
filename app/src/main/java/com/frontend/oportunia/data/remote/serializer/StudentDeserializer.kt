package com.frontend.oportunia.data.remote.serializer
import com.frontend.oportunia.data.remote.dto.StudentDto
import com.frontend.oportunia.data.remote.dto.UserDto
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.Date


class StudentDeserializer : JsonDeserializer<StudentDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): StudentDto {
        val jsonObject = json.asJsonObject

        val user = context.deserialize<UserDto>(jsonObject.get("user"), UserDto::class.java)
        val description = jsonObject.get("description")?.asString ?: ""
        val premium = jsonObject.get("premium")?.asBoolean ?: false
        val linkedinUrl = jsonObject.get("linkedinUrl")?.asString ?: ""
        val githubUrl = jsonObject.get("githubUrl")?.asString ?: ""
        val bornDate = jsonObject.get("bornDate")?.let {
            context.deserialize<Date>(it, Date::class.java)
        } ?: Date()
        val location = jsonObject.get("location")?.asString ?: ""

        return StudentDto(
            user = user,
            description = description,
            premium = premium,
            linkedinUrl = linkedinUrl,
            githubUrl = githubUrl,
            bornDate = bornDate.toString(),
            location = location
        )
        }
    }
