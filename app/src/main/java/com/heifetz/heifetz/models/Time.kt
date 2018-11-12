package com.heifetz.heifetz.models

import android.graphics.Color
import com.heifetz.heifetz.enums.Stops

class Times(val items: ArrayList<Time>)

class Time(id: Int, stop: Stops, val value: String) {

    var id: Int? = id
    var stop: Stops? = stop
    var color: Int = Color.TRANSPARENT

    fun isEnd(): Boolean {
        return stop == Stops.START || stop == Stops.END
    }

}