package com.arctouch.codechallenge.common.model.repository

import android.util.Log
import com.arctouch.codechallenge.common.model.Movie
import com.arctouch.codechallenge.common.model.api.RetrofitClient
import com.arctouch.codechallenge.common.model.api.TmdbApi
import com.arctouch.codechallenge.common.model.data.Cache
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieRepository {
    val client = RetrofitClient.apiInstance

    fun upcomingMovies(): Observable<MutableList<Movie>> {
        return client.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.newThread())
                .flatMap {
                    Cache.cacheGenres(it.genres)
                    client.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1, TmdbApi.DEFAULT_REGION)
                }.map { movieList ->
                    insertGender(movieList.results)
                    movieList.results
                }.doOnError { onErrorResult ->
                    Log.e("MovieRepository", "Erro  ------>", onErrorResult)
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun insertGender(movieList: MutableList<Movie>) {
        movieList.forEach { movieResult ->
            movieResult.genres = ArrayList()
            movieResult.genres.addAll(Cache.genres.filter { movieResult.genreIds?.contains(it.id) == true })
        }
    }

    fun upcomingMovies(page: Long): Observable<MutableList<Movie>> {
        return RetrofitClient.apiInstance.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page, TmdbApi.DEFAULT_REGION)
                .map { movieList ->
                    insertGender(movieList.results)
                    movieList.results
                }.doOnError { onErrorResult ->
                    Log.e("MovieRepository", "Erro  ----->", onErrorResult)
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }
}