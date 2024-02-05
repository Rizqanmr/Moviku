package com.rizqanmr.moviku.datasources

import com.rizqanmr.moviku.network.ApiService
import com.rizqanmr.moviku.network.model.GenresModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun getGenres(): GenresModel {
        return withContext(coroutineContext) {
            try {
                val genres = apiService.getGenres()
                genres
            } catch (e: Exception) {
                GenresModel(listOf())
            }
        }
    }
}