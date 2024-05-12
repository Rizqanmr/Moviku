package com.rizqanmr.moviku.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rizqanmr.moviku.network.model.GenresItem
import com.rizqanmr.moviku.utils.Constant.EXTRA_GENRE_ID
import com.rizqanmr.moviku.view.discover.DiscoverFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {

    private var genre = listOf<GenresItem>()

    fun setGenre(genre: List<GenresItem>) {
        this.genre = genre
        notifyItemRangeInserted(0, genre.size)
    }

    fun getGenreName(position: Int): String {
        return genre[position].getGenreName()
    }

    override fun getItemCount(): Int  = genre.size

    override fun createFragment(position: Int): Fragment {
        val fragment = DiscoverFragment()
        val genre = genre[position]
        fragment.arguments = Bundle().apply {
            genre.id?.let { putInt(EXTRA_GENRE_ID, it) }
        }
        return fragment
    }
}