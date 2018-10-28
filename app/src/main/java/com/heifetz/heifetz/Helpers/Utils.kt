package com.heifetz.heifetz.Helpers

import android.graphics.Color
import com.heifetz.heifetz.models.TimeItem
import com.heifetz.heifetz.models.Times
import java.util.*

fun coloringTimes(times: Times): Times {
    val calendar = Calendar.getInstance()
    var check = true

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
                lastItem.color = Color.GREEN
            }
            time.color = Color.GREEN
            check = check && false
        }


        lastTime = date
        lastItem = time
    }

    if (check) {
        times.items.first().color = Color.parseColor("#fffd61")
        times.items.last().color = Color.parseColor("#fffd61")
    }

    return times
}