package com.rizqanmr.moviku.view.moviedetail

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rizqanmr.moviku.R
import com.rizqanmr.moviku.databinding.ActivityMovieDetailBinding
import com.rizqanmr.moviku.network.model.ItemMovieModel
import com.rizqanmr.moviku.utils.Constant
import com.rizqanmr.moviku.utils.setFitImageUrl

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        fun newIntent(fragment: Fragment, bundle: Bundle?) {
            fragment.startActivity(
                Intent(fragment.context, MovieDetailActivity::class.java).apply {
                    bundle?.let { putExtras(it) }
                }
            )
        }
    }

    private lateinit var binding: ActivityMovieDetailBinding
    private var movie: ItemMovieModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constant.EXTRA_MOVIE, ItemMovieModel::class.java)
        } else {
            intent.getParcelableExtra(Constant.EXTRA_MOVIE)
        }

        setupViewPage()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
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
        }
    }
}