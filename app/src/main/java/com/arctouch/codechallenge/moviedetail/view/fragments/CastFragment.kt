package com.arctouch.codechallenge.moviedetail.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.common.model.Cast
import com.arctouch.codechallenge.common.model.Movie
import com.arctouch.codechallenge.common.util.RxBus
import com.arctouch.codechallenge.common.viewmodel.HomeViewModel
import com.arctouch.codechallenge.moviedetail.view.adapter.ActorAdapter
import kotlinx.android.synthetic.main.activity_detail.*


class CastFragment : Fragment() {

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    lateinit var adapter: ActorAdapter
    var actors: List<Cast> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cast, container, false)

        val castRecycler = view.findViewById<RecyclerView>(R.id.castRecycler)
        val layoutManager = LinearLayoutManager(context)
        castRecycler.layoutManager = layoutManager

        castRecycler.setOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val manager = recyclerView!!.layoutManager as LinearLayoutManager
                    val firstVisiblePosition = manager.findFirstCompletelyVisibleItemPosition()
                    Log.w("CompleteVisiblePosition",  "----------->$firstVisiblePosition")
////                    if (firstVisiblePosition == 0) {
////                        activity!!.detailAppbar.setExpanded(true, true)
////                    }
                }
            }
        })

        RxBus.bus.subscribe {
            val movie = it as Movie
            homeViewModel.getActors(movie.id.toLong())
        }

        homeViewModel.actors.observe(this, Observer {
            adapter = ActorAdapter(it!!)
            castRecycler.layoutManager = LinearLayoutManager(context)
            castRecycler.adapter = adapter
            Log.e("Actors ----->", it.toString())
        })

        return view
    }

}
