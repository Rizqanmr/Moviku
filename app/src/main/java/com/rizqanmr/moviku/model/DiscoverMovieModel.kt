package com.rizqanmr.moviku.model


import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.rizqanmr.moviku.utils.Constant
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.math.*

data class DiscoverMovieModel(
    @SerializedName("page") val page: Int = 0,
    @SerializedName("total_pages") val totalPages: Int = 0,
    @SerializedName("results") val results: List<ItemMovieModel> = emptyList(),
    @SerializedName("total_results") val totalResults: Int = 0
)

@Entity(tableName = "movies")
@Parcelize
data class ItemMovieModel(
    @SerializedName("overview") val overview: String = "",
    @SerializedName("original_language") val originalLanguage: String = "",
    @SerializedName("original_title") val originalTitle: String = "",
    @SerializedName("video") val video: Boolean = false,
    @SerializedName("title") val title: String = "",
    @SerializedName("poster_path") val posterPath: String = "",
    @SerializedName("backdrop_path") val backdropPath: String = "",
    @SerializedName("release_date") val releaseDate: String = "",
    @SerializedName("popularity") val popularity: Double = 0.0,
    @SerializedName("vote_average") val voteAverage: Double = 0.0,
    @PrimaryKey @SerializedName("id") val id: Int = 0,
    @SerializedName("adult") val adult: Boolean = false,
    @SerializedName("vote_count") val voteCount: Int = 0
) : Parcelable {
    @SuppressLint("SimpleDateFormat")
    fun getFormattedDate(): String? {
        return try {
            val date = SimpleDateFormat("yyyy-MM-dd").parse(releaseDate)
            val dateFormat = SimpleDateFormat("dd MMM yyyy")
            date?.let { dateFormat.format(it) }
        } catch (e: Exception) {
            null
        }
    }

    fun getRating(): String {
        return BigDecimal(voteAverage).setScale(1, RoundingMode.HALF_EVEN).toString()
    }

    fun getRatingStar(): Float {
        val rating = voteAverage * 0.5
        return BigDecimal(rating).setScale(1, RoundingMode.HALF_EVEN).toFloat()
    }

    fun getUrlPoster(): String {
        return Constant.URL_POSTER + posterPath
    }

    fun getUrlBackdrop(): String {
        return Constant.URL_BACKDROP + backdropPath
    }
}


