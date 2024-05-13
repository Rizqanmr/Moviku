package com.rizqanmr.moviku.repository

import com.rizqanmr.moviku.datasources.RemoteDataSource
import com.rizqanmr.moviku.model.DetailMovieModel
import com.rizqanmr.moviku.model.DiscoverMovieModel
import com.rizqanmr.moviku.model.GenresModel
import com.rizqanmr.moviku.model.ReviewsModel
import javax.inject.Inject

class AppRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {
    suspend fun getGenres() : GenresModel {
        return remoteDataSource.getGenres()
    }

    suspend fun getDiscoverMovies(page: Int, genreId: Int) : DiscoverMovieModel {
        return remoteDataSource.getDiscoverMovies(page, genreId)
    }

    suspend fun getDetailMovie(movieId: Int?) : DetailMovieModel? {
        return remoteDataSource.getDetailMovie(movieId)
    }

    suspend fun getReview(movieId: Int?, page: Int) : ReviewsModel? {
        return remoteDataSource.getReviews(movieId, page)
    }
}