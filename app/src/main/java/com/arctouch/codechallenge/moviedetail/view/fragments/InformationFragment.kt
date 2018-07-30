package com.arctouch.codechallenge.moviedetail.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.common.model.Movie
import com.arctouch.codechallenge.common.util.RxBus
import com.arctouch.codechallenge.common.util.Utils
import com.arctouch.codechallenge.common.viewmodel.HomeViewModel
import com.arctouch.codechallenge.moviedetail.view.adapter.SimilarAdapter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_information.*


class InformationFragment : Fragment() {

    val movieViewModel : HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    lateinit var disposable : Disposable
    lateinit var movieList : MutableList<Movie>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieList = movieViewModel.similars.value!!
        disposable = RxBus.bus.subscribe {
            val movie = it as Movie
            if(movie.overview!=null)
                infoDescription.text = movie.overview
            else
                infoDescription.text = getString(R.string.overview_dont_found)
            infoReleaseDate.text = Utils.dateFormat(movie.releaseDate)
            movieViewModel.getSimilars(movie.id.toLong())
        }

        infoSimilar.layoutManager = LinearLayoutManager(context,LinearLayout.HORIZONTAL,false)
        infoSimilar.adapter = SimilarAdapter(movieList)

        movieViewModel.similars.observe(this, Observer {
            infoSimilar.adapter.notifyDataSetChanged()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
