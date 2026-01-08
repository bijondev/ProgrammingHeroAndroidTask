package me.bijon.ph_android_task.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import me.bijon.ph_android_task.data.local.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Query("SELECT * FROM courses")
    fun getAllCourses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE title LIKE '%' || :query || '%' OR tags LIKE '%' || :query || '%'")
    fun searchCourses(query: String): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE courseId = :id")
    fun getCourseById(id: String): Flow<CourseEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourses(courses: List<CourseEntity>)

    // We use a custom update to preserve isEnrolled if we were to refresh from network, 
    // but here we just need to update the enrollment status specifically
    @Query("UPDATE courses SET isEnrolled = :isEnrolled WHERE courseId = :courseId")
    suspend fun updateEnrollment(courseId: String, isEnrolled: Boolean)
    
    // For full sync strategy, we might want to upsert but keep local flags.
    // simpler: Insert ignore, then maybe update fields if needed.
    // Or: Fetch existing, map network to entities while preserving locally stored flags.
}
