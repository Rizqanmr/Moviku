package com.rizqanmr.moviku.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rizqanmr.moviku.network.model.ItemMovieModel
import com.rizqanmr.moviku.repository.AppRepository

class MoviePagingSource(
    private val appRepository: AppRepository,
    private val genreId: Int
) : PagingSource<Int, ItemMovieModel>() {
    override fun getRefreshKey(state: PagingState<Int, ItemMovieModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemMovieModel> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = appRepository.getDiscoverMovies(nextPageNumber, genreId)

            LoadResult.Page(
                data = response?.results.orEmpty(),
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (response?.results?.isEmpty() == true) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}