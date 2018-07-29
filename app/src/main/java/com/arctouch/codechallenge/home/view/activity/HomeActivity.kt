package com.arctouch.codechallenge.home.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.common.model.Movie
import com.arctouch.codechallenge.common.util.RxBus
import com.arctouch.codechallenge.home.view.adapter.HomeAdapter
import com.arctouch.codechallenge.home.viewmodel.HomeViewModel
import com.arctouch.codechallenge.movie_detail.view.DetailActivity
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity() {

    private lateinit var adapter: HomeAdapter
    private var isLoading: Boolean = true

    private val movieList = ArrayList<Movie>()

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        homeViewModel.getUpcomingMovies()
        adapter = HomeAdapter(movieList)
        recyclerView.adapter = adapter
        progressBar.visibility = View.GONE

        homeViewModel.movies.observe(this, Observer {
            if (!it!!.isEmpty()) {
                movieList.addAll(it.toList())
                recyclerView.adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, getString(R.string.no_results), Toast.LENGTH_SHORT).show()
            }
            isLoading = true
            dismissLoading()
        })

        adapter.click.subscribe {
            RxBus.bus.onNext(it)
            startActivity(Intent(applicationContext, DetailActivity::class.java))
        }

        recyclerView.setOnScrollListener(object : RecyclerView.OnScrollListener() {

            private var visibleItemCount: Int? = null
            private var totalItemCount: Int? = null
            private var pastVisiblesItems: Int? = null

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {//Scroll down
                    val manager = recyclerView.layoutManager as LinearLayoutManager
                    visibleItemCount = manager.childCount
                    totalItemCount = manager.itemCount
                    pastVisiblesItems = manager.findFirstVisibleItemPosition()
                    if (isLoading) {
                        if ((visibleItemCount!! + pastVisiblesItems!!) >= totalItemCount!!) {
                            showLoading()
                            isLoading = false
                            homeViewModel.page += 1
                            homeViewModel.getUpcomingMovies(homeViewModel.page)
                        }
                    }
                }
            }
        })
    }

    fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    fun dismissLoading() {
        progressBar.visibility = View.GONE
    }
}
