package me.bijon.ph_android_task.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey
    val courseId: String,
    val title: String,
    val descriptionShort: String,
    val instructorName: String, // Flattened for simplicity or could be embedded
    val instructorExpertise: String,
    val durationWeeks: Int,
    val priceUsd: Double,
    val isPremium: Boolean,
    val tags: List<String>, // Needs TypeConverter
    val rating: Double,
    val isEnrolled: Boolean = false // Local state
)
