package com.heifetz.heifetz.models

import android.graphics.Color

class Times(val items: ArrayList<TimeItem>)

class TimeItem(id: Int, val value: String) {

    var id: Int? = id
    var color: Int = Color.TRANSPARENT

}