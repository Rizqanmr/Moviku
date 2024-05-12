package com.rizqanmr.moviku.di

import android.content.Context
import androidx.room.Room
import com.rizqanmr.moviku.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Volatile
    private var movieDatabase: MovieDatabase? = null

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return movieDatabase ?: synchronized(this) {
            movieDatabase ?: Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java, "movie_database"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also { movieDatabase = it }
        }
    }

    @Singleton
    @Provides
    fun provideDao(movieDatabase: MovieDatabase) = movieDatabase.movieDao()
}