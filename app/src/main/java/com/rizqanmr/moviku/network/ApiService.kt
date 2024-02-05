package com.rizqanmr.moviku.network

import com.rizqanmr.moviku.network.model.GenresModel
import retrofit2.http.GET

interface ApiService {

    @GET("genre/movie/list") suspend fun getGenres(): GenresModel
}