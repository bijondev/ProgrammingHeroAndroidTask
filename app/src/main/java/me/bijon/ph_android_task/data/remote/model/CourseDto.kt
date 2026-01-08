package me.bijon.ph_android_task.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseDto(
    @SerialName("course_id") val courseId: String,
    val title: String,
    @SerialName("description_short") val descriptionShort: String,
    val instructor: InstructorDto,
    @SerialName("duration_weeks") val durationWeeks: Int,
    @SerialName("price_usd") val priceUsd: Double,
    @SerialName("is_premium") val isPremium: Boolean,
    val tags: List<String>,
    val rating: Double
)

@Serializable
data class InstructorDto(
    val name: String,
    @SerialName("expertise_level") val expertiseLevel: String
)
