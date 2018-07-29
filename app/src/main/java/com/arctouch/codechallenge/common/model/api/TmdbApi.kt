package com.arctouch.codechallenge.common.model.api

import com.arctouch.codechallenge.common.model.GenreResponse
import com.arctouch.codechallenge.common.model.ImagesResponse
import com.arctouch.codechallenge.common.model.Movie
import com.arctouch.codechallenge.common.model.UpcomingMoviesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    companion object {
        const val URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "1f54bd990f1cdfb230adb312546d765d"
        const val DEFAULT_LANGUAGE = "pt-BR"
        const val DEFAULT_REGION = "BR"
    }

    @GET("genre/movie/list")
    fun genres(
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Observable<GenreResponse>

    @GET("movie/{id}/images")
    fun getImages(
            @Path("id") id : Long,
            @Query("api_key") apiKey: String
    ): Observable<ImagesResponse>


    @GET("movie/upcoming")
    fun upcomingMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Long
    ): Observable<UpcomingMoviesResponse>

    @GET("movie/{id}")
    fun movie(
            @Path("id") id: Long,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Observable<Movie>
}


