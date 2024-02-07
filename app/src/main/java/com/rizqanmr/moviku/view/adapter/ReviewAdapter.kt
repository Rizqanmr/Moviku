package com.rizqanmr.moviku.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rizqanmr.moviku.R
import com.rizqanmr.moviku.databinding.ItemReviewBinding
import com.rizqanmr.moviku.network.model.ReviewItem
import com.rizqanmr.moviku.utils.setCircleImageUrl

class ReviewAdapter : PagingDataAdapter<ReviewItem, ReviewAdapter.ReviewViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReviewItem>() {
            override fun areItemsTheSame(oldItem: ReviewItem, newItem: ReviewItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ReviewItem, newItem: ReviewItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bindData(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    class ReviewViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(review: ReviewItem?) {
            (binding as? ItemReviewBinding)?.let { itemReview ->
                itemReview.item = review
                itemReview.ivReviewer.setCircleImageUrl(review?.authorDetails?.getUrlAvatar(), R.drawable.ic_account_circle)
            }
        }
    }
}