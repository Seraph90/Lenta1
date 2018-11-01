package com.heifetz.heifetz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import com.heifetz.heifetz.helpers.DBHelper

class AddTimeActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_time_activity)

        val dbHelper = DBHelper(this)

        val vButton = findViewById<Button>(R.id.ata_addBtn)
        val vTimePicker = findViewById<TimePicker>(R.id.ata_timePicker)
        vTimePicker.setIs24HourView(true)

        vButton.setOnClickListener {
            val id = dbHelper.insertTime("${vTimePicker.hour}:${vTimePicker.minute}")
            val intent = Intent()
            intent.putExtra("id", id)
            setResult(0, intent)

            finish()
        }
    }

}





