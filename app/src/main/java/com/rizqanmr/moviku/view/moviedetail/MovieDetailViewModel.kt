package com.rizqanmr.moviku.view.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizqanmr.moviku.network.model.DetailMovieModel
import com.rizqanmr.moviku.network.model.ItemMovieModel
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
}