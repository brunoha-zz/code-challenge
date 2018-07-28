package com.arctouch.codechallenge.home.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.common.model.Movie
import com.arctouch.codechallenge.common.model.repository.MovieRepository

class HomeViewModel : ViewModel() {

    private val repository = MovieRepository()

    val movies = MutableLiveData<MutableList<Movie>>()
    var page : Long = 1

    init {
        movies.value = ArrayList()
    }

    fun getUpcomingMovies() {
        repository.upcomingMovies().subscribe { onNextResult ->
            insertMovie(onNextResult)
        }
    }

    fun getUpcomingMovies(page: Long) {
        repository.upcomingMovies(page).subscribe { onNextResult ->
            insertMovie(onNextResult)
        }
    }

    private fun insertMovie(list: MutableList<Movie>) {
        movies.value?.addAll(list)
        movies.postValue(
                list
        )
    }
}