package com.arctouch.codechallenge.common.util

import android.content.Context
import android.icu.util.Calendar
import android.location.Location
import java.util.*


class Utils {
    companion object {
        fun dateFormat(date : String?): String? {
            val datas = date!!.split("-")
            return "${datas[2]}/${datas[1]}/${datas[0]}"
        }
    }

}