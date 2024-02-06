package com.rizqanmr.moviku.view.discover

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizqanmr.moviku.databinding.FragmentDiscoverBinding
import com.rizqanmr.moviku.databinding.ItemMovieBinding
import com.rizqanmr.moviku.network.model.ItemMovieModel
import com.rizqanmr.moviku.utils.Constant.ARG_OBJECT
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
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            viewModel.setExtraData(getInt(ARG_OBJECT))
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
        binding.rvMovie.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = discoverMovieAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter { discoverMovieAdapter.retry() }
            )
        }

        discoverMovieAdapter.setMovieListener(object : DiscoverMovieAdapter.MovieListener {
            override fun onItemClick(itemMovieBinding: ItemMovieBinding, movie: ItemMovieModel?) {
                Toast.makeText(requireContext(), "title: ${movie?.title}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}