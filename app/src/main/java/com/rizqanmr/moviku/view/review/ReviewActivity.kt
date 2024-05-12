package com.rizqanmr.moviku.view.review

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rizqanmr.moviku.R
import com.rizqanmr.moviku.databinding.ActivityReviewBinding
import com.rizqanmr.moviku.utils.Constant
import com.rizqanmr.moviku.adapter.LoadingStateAdapter
import com.rizqanmr.moviku.adapter.ReviewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewActivity : AppCompatActivity() {

    companion object {
        fun newIntent(activity: Activity, bundle: Bundle) {
            activity.startActivity(
                Intent(activity, ReviewActivity::class.java).apply {
                    putExtras(bundle)
                }
            )
        }
    }

    private lateinit var binding: ActivityReviewBinding
    private val viewModel by viewModels<ReviewViewModel>()
    private val reviewAdapter by lazy { ReviewAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.setMovieId(intent.getIntExtra(Constant.EXTRA_MOVIE_ID, 0))
        setupObservers()
        setupViewPage()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun setupObservers() {
        viewModel.getReviews().observe(this) {
            reviewAdapter.submitData(lifecycle, it)
        }
    }

    private fun setupViewPage() {
        with(binding) {
            setSupportActionBar(toolbarReview)
            supportActionBar?.apply {
                setDisplayShowHomeEnabled(true)
                setDisplayHomeAsUpEnabled(true)

                val upArrow = ContextCompat.getDrawable(this@ReviewActivity, R.drawable.ic_arrow_back)
                upArrow?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                setHomeAsUpIndicator(upArrow)
            }

            rvReview.apply {
                layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
                setHasFixedSize(true)
                adapter = reviewAdapter.withLoadStateFooter(
                    footer = LoadingStateAdapter { reviewAdapter.retry() }
                )
            }
        }
    }
}