package com.heifetz.heifetz.helpers

import android.graphics.Color
import com.heifetz.heifetz.enums.Stops
import com.heifetz.heifetz.models.Time
import com.heifetz.heifetz.models.Times
import java.util.*

var selectColor = Color.parseColor("#fffd61")

fun coloringTimes(times: Times): Times {
    val calendar = Calendar.getInstance()
    var check = 0

    var last: Time? = null
    var date: Date

    val nowTime = Date().time

    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)

    var lastTime = calendar.time

    for (time in times.items) {
        if (time.stop != Stops.START) {
            continue
        }

        //TODO Сделать что-то по лучше
        //Нужно для того когда добавляешь текущее время, подкрашивается 3 времени
        if (check == 2) {
            continue
        }

        val hour = time.value.substring(0..1).toInt()
        val minute = time.value.substring(3..4).toInt()

        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        date = calendar.time

        if (nowTime in lastTime.time..date.time) {
            if (last != null) {
                last.color = selectColor
                check++
            }
            time.color = selectColor
            check++
        }

        lastTime = date
        last = time
    }

    if (check < 2) {
        times.items.first().color = selectColor
        times.items.last().color = selectColor
    }

    return times
}