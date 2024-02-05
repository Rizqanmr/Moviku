package com.rizqanmr.moviku.network.model


import com.google.gson.annotations.SerializedName

data class GenresModel(
    @SerializedName("genres") val genres: List<GenresItem>?
)


data class GenresItem(
    @SerializedName("name") val name: String? = "",
    @SerializedName("id") val id: Int? = 0
) {
    fun getGenreName(): String {
        return name.orEmpty()
    }
}


