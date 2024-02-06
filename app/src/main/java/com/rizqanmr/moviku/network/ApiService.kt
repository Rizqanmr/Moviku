package com.rizqanmr.moviku.network

import com.rizqanmr.moviku.network.model.DiscoverMovieModel
import com.rizqanmr.moviku.network.model.GenresModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("genre/movie/list") suspend fun getGenres(): GenresModel

    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("page") page: Int,
        @Query("with_genres") genreId: Int
    ) : DiscoverMovieModel
}