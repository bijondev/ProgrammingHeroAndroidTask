package me.bijon.ph_android_task.domain.repository

import me.bijon.ph_android_task.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun getAllCourses(): Flow<List<Course>>
    fun searchCourses(query: String): Flow<List<Course>>
    fun getCourseById(id: String): Flow<Course?>
    suspend fun refreshCourses(): Result<Unit>
    suspend fun updateEnrollment(courseId: String, isEnrolled: Boolean)
}
