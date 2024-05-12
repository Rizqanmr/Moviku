package com.rizqanmr.moviku.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import com.rizqanmr.moviku.utils.Constant
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat

data class ReviewsModel(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("page") val page: Int = 0,
    @SerializedName("total_pages") val totalPages: Int = 0,
    @SerializedName("results") val results: List<ReviewItem>?,
    @SerializedName("total_results") val totalResults: Int = 0
)

data class ReviewItem(
    @SerializedName("author_details") val authorDetails: AuthorDetails,
    @SerializedName("updated_at") val updatedAt: String = "",
    @SerializedName("author") val author: String = "",
    @SerializedName("created_at") val createdAt: String = "",
    @SerializedName("id") val id: String = "",
    @SerializedName("content") val content: String = "",
    @SerializedName("url") val url: String = ""
) {
    @SuppressLint("SimpleDateFormat")
    fun getFormattedDate(): String {
        val a = try {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(updatedAt)
            val dateFormat = SimpleDateFormat("dd MMMM yyyy")
            date?.let { dateFormat.format(it) }
        } catch (e: Exception) {
            null
        }
        return "on $a"
    }
}

data class AuthorDetails(
    @SerializedName("avatar_path") val avatarPath: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("rating") val rating: Double = 0.0,
    @SerializedName("username") val username: String = ""
) {
    fun getRatingUser(): String {
        return BigDecimal(rating).setScale(1, RoundingMode.HALF_EVEN).toString()
    }

    fun getUrlAvatar(): String {
        return Constant.URL_AVATAR + avatarPath
    }

    fun getReviewerName(): String {
        return name.ifBlank { username }
    }
}