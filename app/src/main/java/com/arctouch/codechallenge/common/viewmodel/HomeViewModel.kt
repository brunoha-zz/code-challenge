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
    var pageUpcomming : Long = 1

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

    fun getActors(id : Long){
        repository.getActiots(id).subscribe { onNextResult ->
            actors.value = onNextResult
        }
    }
}