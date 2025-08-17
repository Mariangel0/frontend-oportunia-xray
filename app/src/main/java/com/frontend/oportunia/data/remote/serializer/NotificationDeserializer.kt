package com.frontend.oportunia.data.remote.serializer



import com.frontend.oportunia.data.remote.dto.NotificationDto
import com.frontend.oportunia.data.remote.dto.UserDto
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.Date

class NotificationDeserializer : JsonDeserializer<NotificationDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): NotificationDto {
        val jsonObject = json.asJsonObject

        val id = jsonObject.get("id").asLong
        val userId = context.deserialize<UserDto>(jsonObject.get("userId"), UserDto::class.java)
        val type = jsonObject.get("type").asString
        val message = jsonObject.get("message").asString

        val date = jsonObject.get("date")?.let {
            context.deserialize<Date>(it, Date::class.java)
        } ?: Date()

        val readed = jsonObject.get("readed").asBoolean

        return NotificationDto(id, userId, type, message, readed, date.toString())
    }
}
