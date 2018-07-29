package com.arctouch.codechallenge.movie_detail.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.common.model.Movie
import com.arctouch.codechallenge.common.util.MovieImageUrlBuilder
import com.arctouch.codechallenge.common.util.RxBus
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.movie_item.view.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        RxBus.bus.subscribe {
            val movie = it as Movie

            Glide.with(applicationContext)
                    .load(movie.posterPath?.let { MovieImageUrlBuilder.buildPosterUrl(it) })
                    .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(detailPoster)

            Glide.with(applicationContext)
                    .load(movie.backdropPath?.let { MovieImageUrlBuilder.buildBackdropUrl(it) })
                    .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(detailImage)



        }
    }
}
