package com.rizqanmr.moviku.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizqanmr.moviku.network.model.GenresModel
import com.rizqanmr.moviku.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _genres = MutableLiveData<GenresModel>()
    val genres: LiveData<GenresModel> = _genres

    fun getGenres() {
        viewModelScope.launch {
            _genres.value = appRepository.getGenres()
        }
    }
}