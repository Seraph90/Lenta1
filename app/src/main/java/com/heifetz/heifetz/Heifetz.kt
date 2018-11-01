package com.heifetz.heifetz

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.heifetz.heifetz.helpers.Adapter
import com.heifetz.heifetz.helpers.DBHelper
import com.heifetz.heifetz.helpers.TABLE_NAME
import com.heifetz.heifetz.helpers.coloringTimes

class Heifetz : AppCompatActivity() {

    private lateinit var vListView: ListView
    private lateinit var vButton: FloatingActionButton

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vListView = findViewById(R.id.ma_listView)
        vButton = findViewById(R.id.ma_addBtn)

        vButton.setOnClickListener {
            startActivityForResult(Intent(this, AddTimeActivity::class.java), 0)
        }

        dbHelper = DBHelper(this)

        showListView()

    }

    override fun onResume() {
        super.onResume()
        showListView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            val id = data.getLongExtra("id", -1)
            if (id > 0) {
                showListView()
            }
        }
    }

    fun removeItem(view: View) {
        val linearLayout = view.parent as LinearLayout
        val textView = linearLayout.getChildAt(0) as TextView
        val id = (textView.text as String?)!!.toInt()

        val db = dbHelper.writableDatabase

        db.delete(TABLE_NAME, "id = $id", null)

        showListView()
    }

    private fun showListView() {
        val times = coloringTimes(dbHelper.getSortedTimes())

        var i = 0

        for ((index, item) in times.items.withIndex()) {
            if (item.color != Color.TRANSPARENT) {
                i = index
                break
            }
        }

        vListView.adapter = Adapter(times.items)
        vListView.setSelection(i)
    }

}





