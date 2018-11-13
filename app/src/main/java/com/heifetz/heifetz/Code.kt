package com.heifetz.heifetz

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.heifetz.heifetz.helpers.generateCode

class Code : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.code)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
    }

}

class DrawView(context: Context, attr: AttributeSet) : View(context, attr) {

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        var code = settings.getString(PREFERENCES_CODE, "")

        val paint = Paint()
        if (canvas != null) {
            val codeHeight = 75F
            val strokeWidth = 4F
            paint.color = Color.BLACK
            paint.strokeWidth = strokeWidth
            val midX = width / 2
            val midY = height / 2
            val str = generateCode(code!!)
            val strWidth = str.length * strokeWidth
            var i = strokeWidth
            for (b in str) {
                if (b == '1') {
                    val x = (midX - strWidth / 2) + 1F * i
                    canvas.drawLine(x, midY - codeHeight, x, midY + codeHeight, paint)
                }
                i += strokeWidth
            }
            paint.textSize = 50F

            code = code.replace("(.{4})".toRegex(), "$1 ")

            val textWidth = paint.measureText(code)

            canvas.drawText(code, midX - textWidth / 2, midY + 130F, paint)
        }
    }

}