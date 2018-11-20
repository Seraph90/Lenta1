package com.heifetz.heifetz

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.heifetz.heifetz.enums.Stops
import com.heifetz.heifetz.helpers.DBHelper

class Settings : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        val settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        val vEditCode = findViewById<EditText>(R.id.s_editCode)
        val vOkButton = findViewById<FloatingActionButton>(R.id.s_saveBtn)
        val vRestoreButton = findViewById<FloatingActionButton>(R.id.s_dbRestoreBtn)

        val code = settings.getString(PREFERENCES_CODE, null)
        if (code != null) {
            vEditCode.setText(code, TextView.BufferType.EDITABLE)
        }

        val vSpinner = findViewById<Spinner>(R.id.s_editStop)
        vSpinner.adapter = ArrayAdapter<Stops>(this, android.R.layout.simple_spinner_dropdown_item, Stops.values())
        val stop = settings.getInt(PREFERENCES_STOP, 0)
        if (stop != 0) {
            vSpinner.setSelection(stop)
        }

        vOkButton.setOnClickListener {
            val editor = settings.edit()
            editor.putString(PREFERENCES_CODE, vEditCode.text.toString())
            editor.putInt(PREFERENCES_STOP, vSpinner.selectedItemPosition)
            editor.apply()

            finish()
        }

        vRestoreButton.setOnClickListener {
            val dbHelper = DBHelper(this)

            dbHelper.restoreDb()
            Toast.makeText(this, R.string.restoreDbDone, Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
    }

    override fun overridePendingTransition(enterAnim: Int, exitAnim: Int) {
        super.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
    }

}

