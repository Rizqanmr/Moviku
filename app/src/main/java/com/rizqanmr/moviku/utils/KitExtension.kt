package com.rizqanmr.moviku.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizqanmr.moviku.R

fun ImageView.setFitImageUrl(url: String?, placeholder: Int?= null) {
    if (url.isNullOrBlank() && (placeholder == null || placeholder == 0)) return

    val glideRequest = Glide.with(context).load(url).fitCenter()
    placeholder?.let {
        glideRequest.placeholder(it).apply(
            RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error)
        )
    }
    glideRequest.into(this)
}

fun ImageView.setCircleImageUrl(url: String?, placeholder: Int?= null) {
    if (url.isNullOrBlank() && (placeholder == null || placeholder == 0)) return

    val glideRequest = Glide.with(context).load(url).circleCrop()
    placeholder?.let {
        glideRequest.placeholder(it)
    }
    glideRequest.into(this)
}