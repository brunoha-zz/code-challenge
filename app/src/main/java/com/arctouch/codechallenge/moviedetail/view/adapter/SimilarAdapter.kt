package com.arctouch.codechallenge.moviedetail.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.common.model.Cast
import com.arctouch.codechallenge.common.util.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.reactivex.subjects.PublishSubject
import com.arctouch.codechallenge.common.model.Movie
import com.arctouch.codechallenge.common.util.Utils
import kotlinx.android.synthetic.main.similar_item.view.*


class SimilarAdapter(private val movies : MutableList<Movie>) : RecyclerView.Adapter<SimilarAdapter.ViewHolder>() {

    val click: PublishSubject<Movie> = PublishSubject.create()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie) {
            itemView.similarName.text = movie.title
            itemView.similarYear.text = Utils.getYear(movie.releaseDate)

            Glide.with(itemView)
                    .load(movie.posterPath?.let { MovieImageUrlBuilder.buildPosterUrl(it) })
                    .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(itemView.similarImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.similar_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(movies[position])
        viewHolder.itemView.setOnClickListener {
            click.onNext(movies[position])
        }
    }
}
