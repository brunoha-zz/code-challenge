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
import kotlinx.android.synthetic.main.actor_item.view.*
import android.support.v4.view.ViewCompat.setActivated



class ActorAdapter(private val actors: List<Cast>) : RecyclerView.Adapter<ActorAdapter.ViewHolder>() {

    val click: PublishSubject<Cast> = PublishSubject.create()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(cast: Cast) {
            itemView.actorName.text = cast.name
            itemView.actorCharacter.text = "${itemView.context.getString(R.string.like)} ${cast.character}"

            Glide.with(itemView)
                    .load(cast.profilePath?.let { MovieImageUrlBuilder.buildPosterUrl(it) })
                    .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(itemView.actorImage)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.actor_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = actors.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(actors[position])
        viewHolder.itemView.setOnClickListener {
            click.onNext(actors[position])
        }
    }
}
