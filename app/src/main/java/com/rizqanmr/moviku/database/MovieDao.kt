package com.rizqanmr.moviku.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.rizqanmr.moviku.model.ItemMovieModel

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(story: List<ItemMovieModel>)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): PagingSource<Int, ItemMovieModel>

    @Query("DELETE FROM movies")
    suspend fun deleteAll()

    @RawQuery(observedEntities = [ItemMovieModel::class])
    fun getAllMoviesSortedByTitle(query: SupportSQLiteQuery): PagingSource<Int, ItemMovieModel>
}