package me.bijon.ph_android_task.domain.model

data class Course(
    val id: String,
    val title: String,
    val description: String,
    val instructorName: String,
    val durationWeeks: Int,
    val price: Double,
    val isPremium: Boolean,
    val tags: List<String>,
    val rating: Double,
    val isEnrolled: Boolean
)
