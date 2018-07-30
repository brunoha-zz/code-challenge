package com.arctouch.codechallenge.moviedetail.view.activities

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.common.model.Movie
import com.arctouch.codechallenge.common.util.MovieImageUrlBuilder
import com.arctouch.codechallenge.common.util.RxBus
import com.arctouch.codechallenge.common.util.Utils
import com.arctouch.codechallenge.common.viewmodel.HomeViewModel
import com.arctouch.codechallenge.moviedetail.view.fragments.CastFragment
import com.arctouch.codechallenge.moviedetail.view.fragments.InformationFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.movie_item.view.*


class DetailActivity : AppCompatActivity() {


    val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        RxBus.bus.subscribe { movie ->
            bindView(movie as Movie)
        }

    }

    private fun bindView(movie: Movie) {
        Glide.with(applicationContext)
                .load(movie.posterPath?.let { MovieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(detailPoster)

        Glide.with(applicationContext)
                .load(movie.backdropPath?.let { MovieImageUrlBuilder.buildBackdropUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(detailImage)

        detailTitle.text = movie.title
        detailRating.text = movie.voteAverage.toString()

        if (movie.genres.isNotEmpty()) {
            var genres = ""
            movie.genres.forEach {
                if (it != movie.genres[movie.genres.size - 1]) {
                    genres += "${it.name}, "
                } else {
                    genres += it.name
                }
                detailGender.text = genres
            }
        }

        setupViewPager(supportFragmentManager)

    }

    fun setupViewPager(fragmentManager: FragmentManager) {
        val tabAdapter = TabAdapter(fragmentManager)
        tabAdapter.add(InformationFragment(), applicationContext.getString(R.string.informations))
        tabAdapter.add(CastFragment(), applicationContext.getString(R.string.casts))

        detailViewPager.adapter = tabAdapter
        detailViewPager.offscreenPageLimit = 0
        detailTabLayout.setupWithViewPager(detailViewPager)
    }
}

class TabAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragments: MutableList<Fragment> = ArrayList()
    private val titles: MutableList<String> = ArrayList()

    fun add(frag: Fragment, title: String) {
        fragments.add(frag)
        titles.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}


