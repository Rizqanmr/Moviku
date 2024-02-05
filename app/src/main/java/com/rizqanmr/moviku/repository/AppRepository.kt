package com.rizqanmr.moviku.repository

import com.rizqanmr.moviku.datasources.RemoteDataSource
import com.rizqanmr.moviku.network.model.GenresModel
import javax.inject.Inject

class AppRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {
    suspend fun getGenres(): GenresModel {
        return remoteDataSource.getGenres()
    }
}