package com.rizqanmr.moviku.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizqanmr.moviku.R
import com.rizqanmr.moviku.adapter.DiscoverMovieAdapter
import com.rizqanmr.moviku.adapter.LoadingStateAdapter
import com.rizqanmr.moviku.databinding.ActivityMainBinding
import com.rizqanmr.moviku.databinding.ItemMovieBinding
import com.rizqanmr.moviku.model.ItemMovieModel
import com.rizqanmr.moviku.utils.Constant
import com.rizqanmr.moviku.view.moviedetail.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val discoverMovieAdapter by lazy { DiscoverMovieAdapter() }
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupViewPage()
        viewModel.getGenres()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sort -> {
                showSortingPopupMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSortingPopupMenu() {
        val view = findViewById<View>(R.id.menu_sort) ?: return
        PopupMenu(this, view).run {
            menuInflater.inflate(R.menu.sorting_menu, menu)

            setOnMenuItemClickListener {
                viewModel.changeSortType(
                    when (it.itemId) {
                        R.id.action_ascending -> Constant.SortType.ASCENDING
                        R.id.action_descending -> Constant.SortType.DESCENDING
                        else -> Constant.SortType.RANDOM
                    }
                )
                true
            }
            show()
        }
    }


    private fun setupObservers() {
//        viewModel.genres.observe(this) {
//            if (it.genres?.isNotEmpty() == true) {
//                viewPagerAdapter.setGenre(it.genres)
//            }
//        }
//        viewModel.sortType.observe(this) {
//            viewPagerAdapter.setSortType(it)
//        }
        viewModel.getMovies().observe(this) {
            discoverMovieAdapter.submitData(lifecycle, it)
        }
    }

    private fun setupViewPage() {
//        val viewPager = binding.viewPager
//        val tabLayout = binding.tabLayout
//
//        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
//        viewPager.adapter = viewPagerAdapter
//
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = (viewPagerAdapter.getGenreName(position))
//        }.attach()

        with(binding) {
            setSupportActionBar(toolbar)
            rvMovie.apply {
                layoutManager = GridLayoutManager(this@MainActivity, 2, RecyclerView.VERTICAL, false)
                setHasFixedSize(true)
                discoverMovieAdapter.addLoadStateListener { loadState ->
                    if (loadState.refresh is LoadState.Loading && discoverMovieAdapter.itemCount == 0) {
                        isVisible = false
                        pbLoading.isVisible = true
                    } else if (loadState.refresh is LoadState.NotLoading && discoverMovieAdapter.itemCount == 0) {
                        isVisible = false
                        pbLoading.isVisible = false
                    } else if (loadState.refresh is LoadState.NotLoading && discoverMovieAdapter.itemCount > 0) {
                        isVisible = true
                        pbLoading.isVisible = false
                    }
                }
                adapter = discoverMovieAdapter.withLoadStateFooter(
                    footer = LoadingStateAdapter { discoverMovieAdapter.retry() }
                )
            }
        }

        discoverMovieAdapter.setMovieListener(object : DiscoverMovieAdapter.MovieListener {
            override fun onItemClick(itemMovieBinding: ItemMovieBinding, movie: ItemMovieModel?) {
                val optionCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@MainActivity,
                        Pair(itemMovieBinding.ivMovie, "poster")
                    )
                MovieDetailActivity.newIntent(this@MainActivity, optionCompat.toBundle()
                    ?.apply { putParcelable(Constant.EXTRA_MOVIE, movie) })
            }
        })
    }
}