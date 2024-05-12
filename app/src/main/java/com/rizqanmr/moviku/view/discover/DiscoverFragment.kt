package com.rizqanmr.moviku.view.discover

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizqanmr.moviku.databinding.FragmentDiscoverBinding
import com.rizqanmr.moviku.databinding.ItemMovieBinding
import com.rizqanmr.moviku.model.ItemMovieModel
import com.rizqanmr.moviku.utils.Constant
import com.rizqanmr.moviku.adapter.DiscoverMovieAdapter
import com.rizqanmr.moviku.adapter.LoadingStateAdapter
import com.rizqanmr.moviku.view.moviedetail.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    private lateinit var binding: FragmentDiscoverBinding
    private val viewModel by viewModels<DiscoverViewModel>()
    private val discoverMovieAdapter by lazy { DiscoverMovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(Constant.EXTRA_GENRE_ID) }?.apply {
            viewModel.setExtraData(getInt(Constant.EXTRA_GENRE_ID))
        }

        setupObservers()
        setupViewPage()
    }

    private fun setupObservers() {
        viewModel.getMovies().observe(viewLifecycleOwner) {
            discoverMovieAdapter.submitData(lifecycle, it)
        }
    }

    private fun setupViewPage() {
        with(binding) {
            rvMovie.apply {
                layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
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
                        requireActivity(),
                        Pair(itemMovieBinding.ivMovie, "poster")
                    )
                MovieDetailActivity.newIntent(this@DiscoverFragment, optionCompat.toBundle()
                    ?.apply { putParcelable(Constant.EXTRA_MOVIE, movie) })
            }
        })
    }
}