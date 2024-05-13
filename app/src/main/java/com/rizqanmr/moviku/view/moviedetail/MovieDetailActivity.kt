package com.rizqanmr.moviku.view.moviedetail

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.rizqanmr.moviku.R
import com.rizqanmr.moviku.databinding.ActivityMovieDetailBinding
import com.rizqanmr.moviku.model.DetailMovieModel
import com.rizqanmr.moviku.model.ItemMovieModel
import com.rizqanmr.moviku.utils.Constant
import com.rizqanmr.moviku.utils.setFitImageUrl
import com.rizqanmr.moviku.adapter.LoadingStateAdapter
import com.rizqanmr.moviku.adapter.ReviewAdapter
import com.rizqanmr.moviku.view.review.ReviewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    companion object {
        fun newIntent(activity: Activity, bundle: Bundle?) {
            activity.startActivity(
                Intent(activity, MovieDetailActivity::class.java).apply {
                    bundle?.let { putExtras(it) }
                }
            )
        }
        fun newIntent(fragment: Fragment, bundle: Bundle?) {
            fragment.startActivity(
                Intent(fragment.context, MovieDetailActivity::class.java).apply {
                    bundle?.let { putExtras(it) }
                }
            )
        }
    }

    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel by viewModels<MovieDetailViewModel>()
    private val reviewAdapter by lazy { ReviewAdapter() }
    private var movie: ItemMovieModel? = null
    private var detailMovie: DetailMovieModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constant.EXTRA_MOVIE, ItemMovieModel::class.java)
        } else {
            intent.getParcelableExtra(Constant.EXTRA_MOVIE)
        }

        viewModel.setExtraData(movie)
        setupObservers()
        setupViewPage()
        viewModel.getDetailMovie()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun setupObservers() {
        viewModel.detail.observe(this) {
            detailMovie = it
        }
        viewModel.getReviews().observe(this) {
            reviewAdapter.submitData(lifecycle, it)
        }
    }

    private fun setupViewPage() {
        with(binding) {
            setSupportActionBar(toolbarMovieDetail)
            supportActionBar?.apply {
                setDisplayShowHomeEnabled(true)
                setDisplayHomeAsUpEnabled(true)

                val upArrow = ContextCompat.getDrawable(this@MovieDetailActivity, R.drawable.ic_arrow_back)
                upArrow?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                setHomeAsUpIndicator(upArrow)
            }

            ivBackdrop.setFitImageUrl(movie?.getUrlBackdrop(), R.drawable.ic_error)
            ivPoster.setFitImageUrl(movie?.getUrlPoster(), R.drawable.ic_error)
            tvTitle.text = movie?.title
            tvRating.text = movie?.getRating()
            ratingBar.rating = movie?.getRatingStar()!!
            tvOverview.text = movie?.overview
            tvReleaseDate.text = movie?.getFormattedDate()
            tvReadAllReview.setOnClickListener {
                ReviewActivity.newIntent(this@MovieDetailActivity, bundleOf().apply {
                    putInt(Constant.EXTRA_MOVIE_ID, movie?.id!!)
                })
            }

            rvReview.apply {
                layoutManager = LinearLayoutManager(this@MovieDetailActivity, RecyclerView.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = reviewAdapter.withLoadStateHeader(
                    header = LoadingStateAdapter { reviewAdapter.retry() }
                )
            }
        }

        setupYoutubePlayerView()
    }

    private fun setupYoutubePlayerView() {
        val youTubePlayerView: YouTubePlayerView = binding.youtubePlayerView

        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoItem = detailMovie?.videos?.results?.find { it.type == Constant.TRAILER }
                val videoId = videoItem?.key.orEmpty()
                youTubePlayer.apply {
                    cueVideo(videoId, 0f)
                }
            }
        })
    }

}