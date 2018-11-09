package com.heifetz.heifetz.helpers

import android.graphics.Color
import com.heifetz.heifetz.models.Times
import java.util.*

var primaryColorSelect = Color.parseColor("#fffd61")
var secondaryColorSelect = Color.parseColor("#5e92f3")

fun coloringTimes(times: Times): Times {
    val calendar = Calendar.getInstance()

    val nowTime = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"

    val prev = times.items.findLast { time -> time.isStart() && time.value <= nowTime }
    val next = times.items.find { time -> time.isStart() && time.value > nowTime }

    if (prev == null || next == null) {
        times.items.first().color = primaryColorSelect
        times.items.last().color = primaryColorSelect
    } else {
        prev.color = primaryColorSelect
        next.color = primaryColorSelect
        val prevByStop = times.items.findLast { time -> !time.isStart() && time.value in prev.value..next.value }

        if (prevByStop != null) {
            prevByStop.color = secondaryColorSelect
        }
    }

    return times
}