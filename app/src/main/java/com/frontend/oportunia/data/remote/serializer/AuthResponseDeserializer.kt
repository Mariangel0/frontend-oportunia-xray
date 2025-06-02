package com.frontend.oportunia.data.remote.serializer

import com.frontend.oportunia.data.remote.dto.auth.AuthResponseDto
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class AuthResponseDeserializer : JsonDeserializer<AuthResponseDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): AuthResponseDto {
        val jsonObject = json.asJsonObject

        return AuthResponseDto(
            token = jsonObject.get("token")?.asString ?: "",
            userId = jsonObject.get("userId")?.asString ?: ""
        )
    }
}