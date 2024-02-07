package com.rizqanmr.moviku.datasources

import com.rizqanmr.moviku.network.ApiService
import com.rizqanmr.moviku.network.model.DetailMovieModel
import com.rizqanmr.moviku.network.model.DiscoverMovieModel
import com.rizqanmr.moviku.network.model.GenresModel
import com.rizqanmr.moviku.network.model.ReviewsModel
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

    suspend fun getGenres() : GenresModel {
        return withContext(coroutineContext) {
            try {
                val genres = apiService.getGenres()
                genres
            } catch (e: Exception) {
                GenresModel(listOf())
            }
        }
    }

    suspend fun getDiscoverMovies(page: Int, genreId: Int) : DiscoverMovieModel? {
        return withContext(coroutineContext) {
            try {
                val movies = apiService.getDiscoverMovies(page, genreId)
                movies
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getDetailMovie(movieId: Int?) : DetailMovieModel? {
        return withContext(coroutineContext) {
            try {
                val movie = apiService.getDetailMovie(movieId, "videos")
                movie
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getReviews(movieId: Int?, page: Int) : ReviewsModel? {
        return withContext(coroutineContext) {
            try {
                val review = apiService.getReviews(movieId, page)
                review
            } catch (e: Exception) {
                null
            }
        }
    }
}