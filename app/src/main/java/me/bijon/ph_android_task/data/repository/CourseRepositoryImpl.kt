package me.bijon.ph_android_task.data.repository

import me.bijon.ph_android_task.data.local.CourseDao
import me.bijon.ph_android_task.data.local.entity.CourseEntity
import me.bijon.ph_android_task.data.remote.CourseService
import me.bijon.ph_android_task.data.remote.model.CourseDto
import me.bijon.ph_android_task.domain.model.Course
import me.bijon.ph_android_task.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val api: CourseService,
    private val dao: CourseDao
) : CourseRepository {

    override fun getAllCourses(): Flow<List<Course>> {
        return dao.getAllCourses().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun searchCourses(query: String): Flow<List<Course>> {
        return dao.searchCourses(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getCourseById(id: String): Flow<Course?> {
        return dao.getCourseById(id).map { it?.toDomain() }
    }

    override suspend fun refreshCourses(): Result<Unit> {
        return try {
            android.util.Log.d("CourseRepository", "Fetching courses from API...")
            val remoteCourses = api.fetchCourses()
            android.util.Log.d("CourseRepository", "Fetched ${remoteCourses.size} courses")
            
            // Get local snapshot to preserve enrollment status
            val localCourses = dao.getAllCourses().firstOrNull() ?: emptyList()

            val newEntities = remoteCourses.map { dto ->
                val isEnrolled = localCourses.find { it.courseId == dto.courseId }?.isEnrolled ?: false
                dto.toEntity(isEnrolled)
            }
            android.util.Log.d("CourseRepository", "Inserting ${newEntities.size} entities into DB")
            dao.insertCourses(newEntities)
            android.util.Log.d("CourseRepository", "Insertion complete")
            Result.success(Unit)
        } catch (e: Exception) {
            android.util.Log.e("CourseRepository", "Error refreshing courses", e)
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun updateEnrollment(courseId: String, isEnrolled: Boolean) {
        dao.updateEnrollment(courseId, isEnrolled)
    }

    private fun CourseEntity.toDomain(): Course {
        return Course(
            id = courseId,
            title = title,
            description = descriptionShort,
            instructorName = instructorName,
            durationWeeks = durationWeeks,
            price = priceUsd,
            isPremium = isPremium,
            tags = tags,
            rating = rating,
            isEnrolled = isEnrolled
        )
    }

    private fun CourseDto.toEntity(isEnrolled: Boolean): CourseEntity {
        return CourseEntity(
            courseId = courseId,
            title = title,
            descriptionShort = descriptionShort,
            instructorName = instructor.name,
            instructorExpertise = instructor.expertiseLevel,
            durationWeeks = durationWeeks,
            priceUsd = priceUsd,
            isPremium = isPremium,
            tags = tags,
            rating = rating,
            isEnrolled = isEnrolled
        )
    }
}
