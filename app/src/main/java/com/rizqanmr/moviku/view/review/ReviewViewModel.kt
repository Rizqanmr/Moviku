package com.rizqanmr.moviku.view.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.rizqanmr.moviku.datasources.ReviewPagingSource
import com.rizqanmr.moviku.network.model.ReviewItem
import com.rizqanmr.moviku.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private var movieId: Int = 0

    fun setMovieId(movieId: Int) {
        this.movieId = movieId
    }

    fun getReviews(): LiveData<PagingData<ReviewItem>> {
        return Pager(PagingConfig(pageSize = 5)) {
            ReviewPagingSource(appRepository, movieId, false)
        }.liveData.cachedIn(viewModelScope)
    }
}