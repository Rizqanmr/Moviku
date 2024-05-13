package com.rizqanmr.moviku.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    fun getSortedQuery(sortType: Constant.SortType): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM movies ")
        when (sortType) {
            Constant.SortType.ASCENDING -> {
                simpleQuery.append("ORDER BY title ASC")
            }
            Constant.SortType.DESCENDING -> {
                simpleQuery.append("ORDER BY title DESC")
            }
            Constant.SortType.RANDOM -> {
                simpleQuery.append("ORDER BY RANDOM()")
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}