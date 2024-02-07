package com.rizqanmr.moviku.network

import com.rizqanmr.moviku.network.model.DetailMovieModel
import com.rizqanmr.moviku.network.model.DiscoverMovieModel
import com.rizqanmr.moviku.network.model.GenresModel
import com.rizqanmr.moviku.network.model.ReviewsModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("genre/movie/list") suspend fun getGenres(): GenresModel

    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("page") page: Int,
        @Query("with_genres") genreId: Int
    ) : DiscoverMovieModel

    @GET("movie/{movieId}")
    suspend fun getDetailMovie(
        @Path("movieId") movieId: Int?,
        @Query("append_to_response") appendToResponse: String
    ) : DetailMovieModel

    @GET("movie/{movieId}/reviews")
    suspend fun getReviews(
        @Path("movieId") movieId: Int?,
        @Query("page") page: Int
    ) : ReviewsModel
}