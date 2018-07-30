package com.arctouch.codechallenge.common.model.data

import com.arctouch.codechallenge.common.model.Genre

object Cache {

    var genres = listOf<Genre>()

    fun cacheGenres(genres: List<Genre>) {
        this.genres = genres
    }
}
