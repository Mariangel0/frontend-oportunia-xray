package com.frontend.oportunia.data.remote.serializer
//
//import com.frontend.oportunia.data.remote.dto.EducationDto
//import com.frontend.oportunia.data.remote.dto.StudentDto
//import com.google.gson.JsonDeserializationContext
//import com.google.gson.JsonDeserializer
//import com.google.gson.JsonElement
//import com.google.gson.JsonParseException
//import java.lang.reflect.Type
//
//class EducationDeserializer : JsonDeserializer<EducationDto> {
//    override fun deserialize(
//        json: JsonElement,
//        typeOfT: Type,
//        context: JsonDeserializationContext
//    ): EducationDto {
//        val jsonObject = json.asJsonObject
//
//        val id = if (jsonObject.has("id") && !jsonObject.get("id").isJsonNull) {
//            jsonObject.get("id").asLong
//        } else null
//
//        val name = jsonObject.get("name").asString
//        val institution = jsonObject.get("institution").asString
//        val year = jsonObject.get("year").asInt
//
//        val studentDto = if (jsonObject.has("student") && !jsonObject.get("student").isJsonNull) {
//            context.deserialize<StudentDto>(jsonObject.get("student"), StudentDto::class.java)
//        } else {
//            throw JsonParseException("Education must contain non-null student")
//        }
//
//        return EducationDto(
//            id = id,
//            student = studentDto,
//            name = name,
//            institution = institution,
//            year = year
//        )
//    }
//}
