package com.arctouch.codechallenge.common.model

import com.squareup.moshi.Json

data class GenreResponse(val genres: List<Genre>)

data class Genre(val id: Int, val name: String)

data class UpcomingMoviesResponse(
        val page: Int,
        val results: MutableList<Movie>,
        @Json(name = "total_pages") val totalPages: Int,
        @Json(name = "total_results") val totalResults: Int
)

data class Movie(
        val id: Int,
        val title: String,
        val overview: String?,
        var genres: MutableList<Genre>,
        @Json(name = "genre_ids") val genreIds: List<Int>?,
        @Json(name = "poster_path") val posterPath: String?,
        @Json(name = "backdrop_path") val backdropPath: String?,
        @Json(name = "release_date") val releaseDate: String?,
        @Json(name="vote_average") val voteAverage: Double
)

data class Poster(
        @Json(name = "aspect_ratio") val aspectRatio: Double,
        @Json(name = "file_path") val filePath: String,
        @Json(name = "height") val height: Int,
        @Json(name = "iso_639_1") val iso6391: String,
        @Json(name = "vote_average") val voteAverage: Double,
        @Json(name = "vote_count") val voteCount: Int,
        @Json(name = "width") val width: Int
)

data class ImagesResponse (
        @Json(name = "id") val id: Int,
        @Json(name = "backdrops") val backdrops: List<Backdrop>,
        @Json(name = "posters") val posters: List<Poster>
)

data class Backdrop(
        @Json(name = "aspect_ratio") val aspectRatio: Double,
        @Json(name = "file_path") val filePath: String,
        @Json(name = "height") val height: Int,
        @Json(name = "iso_639_1") val iso6391: Any?,
        @Json(name = "vote_average") val voteAverage: Double,
        @Json(name = "vote_count") val voteCount: Int,
        @Json(name = "width") val width: Int
)

data class CreditResponse(
        @Json(name = "id") val id: Int,
        @Json(name = "cast") val cast: List<Cast>,
        @Json(name = "crew") val crew: List<Crew>
)

data class Crew(
        @Json(name = "credit_id") val creditId: String,
        @Json(name = "department") val department: String,
        @Json(name = "gender") val gender: Int,
        @Json(name = "id") val id: Int,
        @Json(name = "job") val job: String,
        @Json(name = "name") val name: String,
        @Json(name = "profile_path") val profilePath: Any?
)


data class Cast(
        @Json(name = "cast_id") val castId: Int,
        @Json(name = "character") val character: String,
        @Json(name = "credit_id") val creditId: String,
        @Json(name = "gender") val gender: Int,
        @Json(name = "id") val id: Int,
        @Json(name = "name") val name: String,
        @Json(name = "order") val order: Int,
        @Json(name = "profile_path") val profilePath: String?
)