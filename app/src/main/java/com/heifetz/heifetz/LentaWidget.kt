package com.heifetz.heifetz

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.Color
import android.widget.RemoteViews
import com.heifetz.heifetz.Helpers.DBHelper
import com.heifetz.heifetz.Helpers.coloringTimes

class LentaWidget : AppWidgetProvider() {

    private lateinit var dbHelper: DBHelper

    private fun updateAppWidget(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetId: Int) {
        dbHelper = DBHelper(context!!)

        val times = coloringTimes(dbHelper.getSortedTimes())

        var text1: String? = null
        var text2: String? = null

        for (time in times.items) {
            if (time.color != Color.TRANSPARENT) {
                if (text1 == null) {
                    text1 = time.value
                } else if (text2 == null) {
                    text2 = time.value
                }
            }
        }

        val views = RemoteViews(context.packageName, R.layout.lenta_widget)

        views.setTextViewText(R.id.lw_textView1, text1)
        views.setTextViewText(R.id.lw_textView2, text2)

        appWidgetManager?.updateAppWidget(appWidgetId, views)
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        if (appWidgetIds != null) {
            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId)
            }
        }
    }

}
