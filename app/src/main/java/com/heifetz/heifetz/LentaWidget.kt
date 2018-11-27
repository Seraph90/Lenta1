package com.heifetz.heifetz

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.Color
import android.widget.RemoteViews
import com.heifetz.heifetz.enums.Stops
import com.heifetz.heifetz.helpers.DBHelper
import com.heifetz.heifetz.helpers.coloringTimes
import com.heifetz.heifetz.helpers.primaryColorSelect
import java.util.*

class LentaWidget : AppWidgetProvider() {

    private val timer = Timer()

    private fun startTimer(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        timer.schedule(object : TimerTask() {
            override fun run() {
                if (appWidgetIds != null) {
                    for (appWidgetId in appWidgetIds) {
                        updateAppWidget(context, appWidgetManager, appWidgetId)
                    }
                }
            }
        }, 0, 30000)
    }

    private fun stopTimer() {
        timer.cancel()
    }

    private fun updateAppWidget(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetId: Int) {
        val dbHelper = DBHelper(context!!)

        val times = coloringTimes(dbHelper.getSortedTimes(Stops.START))

        var text1: String? = null
        var text2: String? = null

        for (time in times.items) {
            if (time.color == primaryColorSelect) {
                if (text1 == null) {
                    text1 = time.value
                } else if (text2 == null) {
                    text2 = time.value
                }
            }
        }

        val views = RemoteViews(context.packageName, R.layout.lenta_widget)

        if (times.items.first().color != Color.TRANSPARENT && times.items.last().color != Color.TRANSPARENT) {
            text1 = text2.also { text2 = text1 }
        }
        views.setTextViewText(R.id.lw_textView1, text1)
        views.setTextViewText(R.id.lw_textView2, text2)

        appWidgetManager?.updateAppWidget(appWidgetId, views)
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        stopTimer()
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        startTimer(context, appWidgetManager, appWidgetIds)
    }

}
