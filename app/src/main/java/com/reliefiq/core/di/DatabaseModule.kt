package com.reliefiq.core.di

import android.content.Context
import androidx.room.Room
import com.reliefiq.data.local.database.ReliefIQDatabase
import com.reliefiq.data.local.database.TaskDao
import com.reliefiq.data.local.database.VolunteerDao
import com.reliefiq.data.local.database.ReportDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ReliefIQDatabase {
        return Room.databaseBuilder(
            context,
            ReliefIQDatabase::class.java,
            "relief_iq_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(database: ReliefIQDatabase): TaskDao = database.taskDao()

    @Provides
    @Singleton
    fun provideVolunteerDao(database: ReliefIQDatabase): VolunteerDao = database.volunteerDao()

    @Provides
    @Singleton
    fun provideReportDao(database: ReliefIQDatabase): ReportDao = database.reportDao()
}
