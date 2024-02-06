package com.rizqanmr.moviku.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.rizqanmr.moviku.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupViewPage()
        viewModel.getGenres()
    }

    private fun setupObservers() {
        viewModel.genres.observe(this) {
            if (it.genres?.isNotEmpty() == true) {
                viewPagerAdapter.setGenre(it.genres)
            }
        }
    }

    private fun setupViewPage() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = (viewPagerAdapter.getGenreName(position))
        }.attach()
    }
}