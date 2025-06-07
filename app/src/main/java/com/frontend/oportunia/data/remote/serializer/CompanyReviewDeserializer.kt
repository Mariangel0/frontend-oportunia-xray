package com.frontend.oportunia.data.remote.serializer
import com.frontend.oportunia.data.remote.dto.CompanyDto
import com.frontend.oportunia.data.remote.dto.CompanyReviewDto
import com.frontend.oportunia.data.remote.dto.CompanySDto
import com.frontend.oportunia.data.remote.dto.StudentDto
import com.frontend.oportunia.data.remote.dto.StudentRDto
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.Date


class CompanyReviewDeserializer : JsonDeserializer<CompanyReviewDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): CompanyReviewDto {
        val jsonObject = json.asJsonObject

        val id = jsonObject.get("id").asLong
        val rating = jsonObject.get("rating").asFloat
        val comment = jsonObject.get("comment").asString

        val createdAt = jsonObject.get("createdAt")?.let {
            context.deserialize<Date>(it, Date::class.java)
        } ?: Date()

        val studentJson = jsonObject.get("student") // ✅ nombre real
        val companyJson = jsonObject.get("company") // ✅ nombre real


        val studentDto = context.deserialize<StudentRDto>(studentJson, StudentRDto::class.java)
        val companyDto = context.deserialize<CompanySDto>(companyJson, CompanySDto::class.java)

        return CompanyReviewDto(
            id = id,
            studentId = studentDto,
            companyId = companyDto,
            rating = rating,
            comment = comment,
            createdAt = createdAt.toString() // formatealo si es necesario
        )
    }
}

