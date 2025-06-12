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
        val jsonObject = when {
            json.isJsonObject -> json.asJsonObject
            json.isJsonArray -> {
                val array = json.asJsonArray
                if (array.size() > 0) array[0].asJsonObject
                else throw IllegalStateException("Se esperaba al menos un estudiante en el array")
            }
            else -> throw IllegalStateException("Respuesta inesperada del servidor: $json")
        }

        val id          = jsonObject.get("id")?.asLong ?: 0L
        val description = jsonObject.get("description")?.asString
        val premium     = jsonObject.get("premiun")?.asBoolean // << nota, corregido más abajo
        val linkedinUrl = jsonObject.get("linkedinUrl")?.asString
        val githubUrl   = jsonObject.get("githubUrl")?.asString
        val bornDate    = jsonObject.get("bornDate")?.asString
        val location    = jsonObject.get("location")?.asString

        val user = jsonObject.get("user")?.let {
            context.deserialize<UserDto>(it, UserDto::class.java)
        }

        val userId = jsonObject.get("userId")?.asLong

        return StudentDto(
            user        = user,
            userId      = userId,
            description = description,
            premium     = premium,
            linkedinUrl = linkedinUrl,
            githubUrl   = githubUrl,
            bornDate    = bornDate,
            location    = location
        )
    }
}
