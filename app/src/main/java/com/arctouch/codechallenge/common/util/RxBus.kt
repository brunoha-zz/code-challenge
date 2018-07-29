package com.arctouch.codechallenge.common.util

import io.reactivex.subjects.BehaviorSubject

class RxBus {
    companion object {
        val bus : BehaviorSubject<Any> = BehaviorSubject.create()
    }
}