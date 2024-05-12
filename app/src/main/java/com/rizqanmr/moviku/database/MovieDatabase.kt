package com.rizqanmr.moviku.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rizqanmr.moviku.model.ItemMovieModel

@Database(
    entities = [ItemMovieModel::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}