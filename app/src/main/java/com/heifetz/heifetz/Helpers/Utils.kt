package com.heifetz.heifetz.Helpers

import android.graphics.Color
import com.heifetz.heifetz.models.TimeItem
import com.heifetz.heifetz.models.Times
import java.util.*

var selectColor = Color.parseColor("#fffd61")

fun coloringTimes(times: Times): Times {


    val calendar = Calendar.getInstance()
    var check = 0

    var lastItem: TimeItem? = null
    var date: Date

    val nowTime = Date().time

    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)

    var lastTime = calendar.time

    for (time in times.items) {
        val hour = time.value.substring(0..1).toInt()
        val minute = time.value.substring(3..4).toInt()

        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        date = calendar.time

        if (nowTime in lastTime.time..date.time) {
            if (lastItem != null) {
                lastItem.color = selectColor
            }
            time.color = selectColor
            check++
        }


        lastTime = date
        lastItem = time
    }

    if (check < 2) {
        times.items.first().color = selectColor
        times.items.last().color = selectColor
    }

    return times
}