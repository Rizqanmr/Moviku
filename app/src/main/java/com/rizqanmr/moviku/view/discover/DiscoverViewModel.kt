package com.rizqanmr.moviku.view.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.rizqanmr.moviku.datasources.MoviePagingSource
import com.rizqanmr.moviku.model.ItemMovieModel
import com.rizqanmr.moviku.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private var genreId = 0
    fun setExtraData(genreId: Int) {
        this.genreId = genreId
    }

    fun getMovies(): LiveData<PagingData<ItemMovieModel>> {
        return Pager(PagingConfig(pageSize = 10)) {
            MoviePagingSource(appRepository, genreId)
        }.liveData.cachedIn(viewModelScope)
    }
}