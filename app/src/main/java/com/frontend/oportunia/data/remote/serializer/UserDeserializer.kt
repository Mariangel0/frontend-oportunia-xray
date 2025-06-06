package com.frontend.oportunia.data.remote.serializer

import com.frontend.oportunia.data.remote.dto.UserDto
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.Date

class UserDeserializer : JsonDeserializer<UserDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): UserDto {
        val jsonObject = json.asJsonObject

        val id = jsonObject.get("id").asLong

        val createDate: Date = jsonObject.get("createDate")?.let {
            context.deserialize(it, Date::class.java)
        } ?: Date()

        val email = jsonObject.get("email").asString
        val enabled = jsonObject.get("enabled").asBoolean
        val firstName = jsonObject.get("firstName").asString
        val lastName = jsonObject.get("lastName").asString
        val password = jsonObject.get("password").asString
        val tokenExpired = jsonObject.get("tokenExpired").asBoolean



        return UserDto(id, createDate, email, enabled, firstName, lastName, password, tokenExpired)
    }
}
