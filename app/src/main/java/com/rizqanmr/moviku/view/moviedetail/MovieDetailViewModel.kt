package com.rizqanmr.moviku.view.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.rizqanmr.moviku.datasources.ReviewPagingSource
import com.rizqanmr.moviku.network.model.DetailMovieModel
import com.rizqanmr.moviku.network.model.ItemMovieModel
import com.rizqanmr.moviku.network.model.ReviewItem
import com.rizqanmr.moviku.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private var movie: ItemMovieModel? = null

    private val _detail = MutableLiveData<DetailMovieModel>()
    val detail: LiveData<DetailMovieModel> = _detail

    fun setExtraData(movie: ItemMovieModel?) {
        this.movie = movie
    }

    fun getDetailMovie() {
        viewModelScope.launch {
            _detail.value = appRepository.getDetailMovie(movie?.id)
        }
    }

    fun getReviews(): LiveData<PagingData<ReviewItem>> {
        return Pager(PagingConfig(pageSize = 1)) {
            ReviewPagingSource(appRepository, movie?.id, true)
        }.liveData.cachedIn(viewModelScope)
    }
}