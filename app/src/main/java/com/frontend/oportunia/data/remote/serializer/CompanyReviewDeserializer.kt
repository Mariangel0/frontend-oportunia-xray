package com.frontend.oportunia.data.remote.serializer
import com.frontend.oportunia.data.remote.dto.CompanyDto
import com.frontend.oportunia.data.remote.dto.CompanyReviewDto
import com.frontend.oportunia.data.remote.dto.StudentDto
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.Date


class CompanyReviewDeserializer : JsonDeserializer<CompanyReviewDto> {
    override fun deserialize(json: JsonElement,
                             typeOfT: Type,
                             context: JsonDeserializationContext
    ): CompanyReviewDto {
        val jsonObject = json.asJsonObject

        val id = jsonObject.get("id").asLong
        val studentId = jsonObject.get("studentId").asLong
        val companyId = jsonObject.get("companyId").asLong

        val rating = jsonObject.get("rating").asFloat
        val comment = jsonObject.get("comment").asString

        val createdAt = jsonObject.get("createdAt")?.let {
            context.deserialize<Date>(it, Date::class.java)
        } ?: Date()

        return CompanyReviewDto(id, studentId, companyId, rating, comment, createdAt.toString())
    }
}
