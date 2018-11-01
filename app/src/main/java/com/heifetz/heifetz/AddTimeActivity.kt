package com.heifetz.heifetz

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.heifetz.heifetz.helpers.DBHelper
import com.heifetz.heifetz.helpers.TABLE_NAME
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class AddTimeActivity : Activity() {

    lateinit var vEdit: EditText
    lateinit var vButton: Button

    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_time_activity)

        dbHelper = DBHelper(this)

        vEdit = findViewById(R.id.ata_value)
        vButton = findViewById(R.id.ata_addBtn)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        vEdit.setText(dateFormat.format(Date().time))

        vButton.setOnClickListener {

            if (checkMask()) {
                val contentValues = ContentValues()
                val value = vEdit.text.toString() as String?

                val db = dbHelper.writableDatabase

                contentValues.put("value", value)
                val id = db.insert(TABLE_NAME, null, contentValues)
                db.close()

                val intent = Intent()
                intent.putExtra("id", id)
                setResult(0, intent)

                vEdit.setTextColor(Color.BLACK)
                finish()
            } else {
                vEdit.setTextColor(Color.RED)
            }

        }

    }

    private fun checkMask(): Boolean {
        val value = vEdit.text.toString() as String?
        val p = Pattern.compile("^(\\d{2}):(\\d{2})$")
        val m = p.matcher(value)

        if (m.matches()) {
            return m.group(1).toInt() in 0..23 && m.group(2).toInt() in 0..59
        }

        return false
    }

}





