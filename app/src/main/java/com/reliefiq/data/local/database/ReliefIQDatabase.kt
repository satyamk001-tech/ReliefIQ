package com.reliefiq.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks") data class TaskEntity(@PrimaryKey val id: String)
@Entity(tableName = "volunteers") data class VolunteerEntity(@PrimaryKey val id: String)
@Entity(tableName = "reports") data class ReportEntity(@PrimaryKey val id: String)

@Database(
    entities = [TaskEntity::class, VolunteerEntity::class, ReportEntity::class],
    version = 1,
    exportSchema = false
)
// @TypeConverters(Converters::class) // To be implemented for List<String> etc.
abstract class ReliefIQDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun volunteerDao(): VolunteerDao
    abstract fun reportDao(): ReportDao
}

// Dummy DAOs to compile DatabaseModule
@Dao interface TaskDao
@Dao interface VolunteerDao
@Dao interface ReportDao
