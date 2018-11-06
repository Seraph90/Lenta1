package com.heifetz.heifetz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.*
import com.heifetz.heifetz.helpers.DBHelper
import com.heifetz.heifetz.enums.Stops

class AddTimeActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_time_activity)

        val dbHelper = DBHelper(this)

        val vSpinner = findViewById<Spinner>(R.id.ata_stop)
        val vButton = findViewById<FloatingActionButton>(R.id.ata_addBtn)
        val vTimePicker = findViewById<TimePicker>(R.id.ata_timePicker)
        vTimePicker.setIs24HourView(true)

        vSpinner.adapter = ArrayAdapter<Stops>(this, android.R.layout.simple_spinner_dropdown_item, Stops.values())
        vSpinner.setSelection(1)

        vButton.setOnClickListener {
            val id = dbHelper.insertTime(
                "${vTimePicker.hour}:${vTimePicker.minute}",
                Stops.values()[vSpinner.selectedItemPosition].name
            )
            val intent = Intent()
            intent.putExtra("id", id)
            setResult(0, intent)

            finish()
        }
    }

}




