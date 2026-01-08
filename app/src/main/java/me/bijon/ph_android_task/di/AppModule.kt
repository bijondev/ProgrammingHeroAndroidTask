package me.bijon.ph_android_task.di

import android.app.Application
import androidx.room.Room
import me.bijon.ph_android_task.data.local.AppDatabase
import me.bijon.ph_android_task.data.local.CourseDao
import me.bijon.ph_android_task.data.remote.CourseService
import me.bijon.ph_android_task.data.repository.CourseRepositoryImpl
import me.bijon.ph_android_task.domain.repository.CourseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                logger = object : io.ktor.client.plugins.logging.Logger {
                    override fun log(message: String) {
                        android.util.Log.d("KtorRequest", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                val jsonConfig = Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    coerceInputValues = true
                    isLenient = true
                }
                json(jsonConfig)
                json(jsonConfig, io.ktor.http.ContentType.Text.Plain)
            }
        }
    }

    @Provides
    @Singleton
    fun provideCourseService(client: HttpClient): CourseService {
        return CourseService(client)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "course_db"
        ).fallbackToDestructiveMigration()
         .build()
    }

    @Provides
    @Singleton
    fun provideCourseDao(db: AppDatabase): CourseDao {
        return db.courseDao()
    }

    @Provides
    @Singleton
    fun provideCourseRepository(
        api: CourseService,
        dao: CourseDao
    ): CourseRepository {
        return CourseRepositoryImpl(api, dao)
    }
}
