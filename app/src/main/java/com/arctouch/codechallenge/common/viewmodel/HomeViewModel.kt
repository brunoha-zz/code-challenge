package com.arctouch.codechallenge.common.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.common.model.Cast
import com.arctouch.codechallenge.common.model.Movie
import com.arctouch.codechallenge.common.model.repository.MovieRepository

class HomeViewModel : ViewModel() {

    private val repository = MovieRepository()

    val movies = MutableLiveData<MutableList<Movie>>()
    val actors = MutableLiveData<List<Cast>>()
    val similars = MutableLiveData<MutableList<Movie>>()
    var pageSimilars: Long = 1
    var pageUpcomming: Long = 1

    init {
        movies.value = ArrayList()
        similars.value = ArrayList()
    }

    fun getUpcomingMovies() {
        if (movies.value!!.isEmpty())
            repository.upcomingMovies().subscribe { onNextResult ->
                insertMovie(onNextResult)
            }
    }

    fun getSimilars(id: Long) {
        repository.getSimilars(id).subscribe { onNextResult ->
            val movieList = similars.value
            movieList!!.addAll(onNextResult)
            similars.value = movieList
        }

    }

    fun getUpcomingMovies(page: Long) {
        repository.upcomingMovies(page).subscribe { onNextResult ->
            insertMovie(onNextResult)
        }
    }

    private fun insertMovie(list: MutableList<Movie>) {
        val movieList = movies.value
        movieList!!.addAll(list)
        movies.value = movieList
    }

    fun getActors(id: Long) {
        repository.getActiots(id).subscribe { onNextResult ->
            actors.value = onNextResult
        }
    }
}