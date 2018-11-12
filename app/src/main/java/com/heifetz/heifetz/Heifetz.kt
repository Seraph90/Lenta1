package com.heifetz.heifetz

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.heifetz.heifetz.enums.Stops
import com.heifetz.heifetz.helpers.Adapter
import com.heifetz.heifetz.helpers.DBHelper
import com.heifetz.heifetz.helpers.TIMES_TABLE_NAME
import com.heifetz.heifetz.helpers.coloringTimes

class Heifetz : AppCompatActivity() {

    private lateinit var vListView: ListView
    private lateinit var vAddButton: FloatingActionButton
    private lateinit var vShowCardButton: FloatingActionButton

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botNav = findViewById<BottomNavigationView>(R.id.ma_botNav)
        botNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.botNavMenuHome -> {
                    showListView(Stops.START)
                }
                R.id.botNavMenuLenta -> {
                    showListView(Stops.END)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

        vListView = findViewById(R.id.ma_listView)
        vAddButton = findViewById(R.id.ma_addBtn)
        vShowCardButton = findViewById(R.id.ma_showCode)

        vAddButton.setOnClickListener {
            startActivityForResult(Intent(this, AddTimeActivity::class.java), 0)
        }

        vShowCardButton.setOnClickListener {
            startActivityForResult(Intent(this, Code::class.java), 0)
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

        db.delete(TIMES_TABLE_NAME, "id = $id", null)

        showListView()
    }

    private fun showListView(stop: Stops = Stops.START) {
        val times = coloringTimes(dbHelper.getSortedTimes(stop))

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu!!.add(0, 0, 0, R.string.restoreDb)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            0 -> {
                dbHelper.restoreDb()
                Toast.makeText(this, R.string.restoreDbDone, Toast.LENGTH_LONG).show()
                showListView()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}





