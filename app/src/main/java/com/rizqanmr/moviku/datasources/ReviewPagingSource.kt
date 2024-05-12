package com.rizqanmr.moviku.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rizqanmr.moviku.model.ReviewItem
import com.rizqanmr.moviku.repository.AppRepository

class ReviewPagingSource(
    private val appRepository: AppRepository,
    private val movieId: Int?,
    private val isDetail: Boolean ?= false
) : PagingSource<Int, ReviewItem>() {

    override fun getRefreshKey(state: PagingState<Int, ReviewItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewItem> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = appRepository.getReview(movieId, nextPageNumber)
            val oneData = response?.results?.take(1).orEmpty()

            if (isDetail == true) {
                LoadResult.Page(
                    data = oneData,
                    prevKey = null,
                    nextKey = null
                )
            } else {
                LoadResult.Page(
                    data = response?.results.orEmpty(),
                    prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                    nextKey = if (response?.results?.isEmpty() == true) null else nextPageNumber + 1
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}