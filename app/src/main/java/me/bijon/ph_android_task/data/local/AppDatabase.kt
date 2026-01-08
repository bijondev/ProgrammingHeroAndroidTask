package me.bijon.ph_android_task.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.bijon.ph_android_task.data.local.entity.CourseEntity

@Database(entities = [CourseEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}
