// src/main/java/com/frontend/oportunia/data/remote/serializer/RoleDeserializer.kt
package com.frontend.oportunia.data.remote.serializer

import com.frontend.oportunia.data.remote.dto.PrivilegeDto
import com.frontend.oportunia.data.remote.dto.RoleDto
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class RoleDeserializer : JsonDeserializer<RoleDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): RoleDto {
        val obj = json.asJsonObject

        // ID y nombre (asegúrate de ajustar los nombres de campo si difieren)
        val id = obj.get("id")?.asLong ?: 0L
        val name = obj.get("name")?.asString ?: ""

        // Privileges: puede venir null => devolvemos lista vacía
        val privileges = mutableListOf<PrivilegeDto>()
        obj.get("privileges")?.takeIf { it.isJsonArray }?.asJsonArray
            ?.forEach { elem ->
                val privObj = elem.asJsonObject
                val pid = privObj.get("id")?.asLong
                val pname = privObj.get("name")?.asString
                if (pid != null && pname != null) {
                    privileges.add(PrivilegeDto(id = pid, name = pname))
                }
            }

        return RoleDto(
            id = id,
            name = name,
            privileges = privileges
        )
    }
}
