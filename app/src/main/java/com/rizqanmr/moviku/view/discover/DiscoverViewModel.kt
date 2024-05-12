package com.rizqanmr.moviku.view.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.rizqanmr.moviku.database.MovieDatabase
import com.rizqanmr.moviku.datasources.MovieRemoteMediator
import com.rizqanmr.moviku.model.ItemMovieModel
import com.rizqanmr.moviku.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val database: MovieDatabase
) : ViewModel() {

    private var genreId = 0
    fun setExtraData(genreId: Int) {
        this.genreId = genreId
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getMovies(): LiveData<PagingData<ItemMovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            remoteMediator = MovieRemoteMediator(appRepository, database, genreId),
            pagingSourceFactory = {
                database.movieDao().getAllMovies()
            }
        ).liveData.cachedIn(viewModelScope)
    }
}