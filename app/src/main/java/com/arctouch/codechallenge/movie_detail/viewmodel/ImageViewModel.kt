package com.arctouch.codechallenge.movie_detail.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.common.model.Backdrop
import com.arctouch.codechallenge.common.model.repository.MovieRepository

class ImageViewModel : ViewModel() {

    val image: MutableLiveData<Backdrop> = MutableLiveData()

    val movieRepository = MovieRepository()

    fun getImages(id: Long) {
        movieRepository.getImages(id)
                .subscribe {
                    if (it.backdrops.isNotEmpty()) {
                        image.value = it.backdrops[0]
                        image.postValue(it.backdrops[0])
                    }
                }
    }


}