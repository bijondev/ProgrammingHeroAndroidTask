package me.bijon.ph_android_task.data.remote

import me.bijon.ph_android_task.data.remote.model.CourseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CourseService(private val client: HttpClient) {
    
    // Placeholder URL - User needs to create this or update it.
    companion object {
        const val ENDPOINT = "https://mp7eebd038e5f22e63df.free.beeceptor.com/courses"
    }

    suspend fun fetchCourses(): List<CourseDto> {
        return client.get(ENDPOINT).body()
    }
}
