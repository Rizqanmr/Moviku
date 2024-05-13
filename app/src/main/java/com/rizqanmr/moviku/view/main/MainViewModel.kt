package com.rizqanmr.moviku.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.rizqanmr.moviku.database.MovieDatabase
import com.rizqanmr.moviku.datasources.MovieRemoteMediator
import com.rizqanmr.moviku.model.GenresModel
import com.rizqanmr.moviku.model.ItemMovieModel
import com.rizqanmr.moviku.repository.AppRepository
import com.rizqanmr.moviku.utils.Constant
import com.rizqanmr.moviku.utils.SortUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val database: MovieDatabase
) : ViewModel() {

    private val _genres = MutableLiveData<GenresModel>()
    val genres: LiveData<GenresModel> = _genres
    private val _sort = MutableLiveData<Constant.SortType>()
    private val _genreId = MutableLiveData<Int?>()

    init {
        _sort.value = Constant.SortType.RANDOM
        getGenres()
        _genreId.value = 53
    }

    fun getGenres() {
        viewModelScope.launch {
            _genres.value = appRepository.getGenres()
        }
    }

    fun changeSortType(sortType: Constant.SortType) {
        _sort.value = sortType
    }

    fun setGenreId(genreId: Int?) {
        genreId?.let { _genreId.value = it }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getMovies(): LiveData<PagingData<ItemMovieModel>> {
        return _genreId.switchMap { genreId ->
            _sort.switchMap { sortType ->
                val query = SortUtils.getSortedQuery(sortType)

                Pager(
                    config = PagingConfig(pageSize = 10),
                    remoteMediator = MovieRemoteMediator(appRepository, database, genreId!!),
                    pagingSourceFactory = {
                        database.movieDao().getAllMoviesSortedByTitle(query)
                    }
                ).liveData.cachedIn(viewModelScope)
            }
        }
    }
}