package com.heifetz.heifetz.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.heifetz.heifetz.models.TimeItem
import com.heifetz.heifetz.models.Times

const val DB_NAME = "times"
const val TABLE_NAME = "times"

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME (id INTEGER PRIMARY KEY AUTOINCREMENT, value TEXT);")
        db.execSQL(
            "INSERT INTO $TABLE_NAME (value) VALUES " +
                    "('10:25'), " +
                    "('10:50'), " +
                    "('11:10'), " +
                    "('11:30'), " +
                    "('11:50'), " +
                    "('12:10'), " +
                    "('12:35'), " +
                    "('13:00'), " +
                    "('13:25'), " +
                    "('13:45'), " +
                    "('14:10'), " +
                    "('14:35'), " +
                    "('15:00'), " +
                    "('15:25'), " +
                    "('16:10'), " +
                    "('16:35'), " +
                    "('17:00'), " +
                    "('17:25'), " +
                    "('17:50'), " +
                    "('18:15'), " +
                    "('18:40'), " +
                    "('19:05'), " +
                    "('19:30'), " +
                    "('19:55'), " +
                    "('20:20'), " +
                    "('20:45'), " +
                    "('21:10'), " +
                    "('21:25'), " +
                    "('21:50'), " +
                    "('22:05');"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    fun getSortedTimes(): Times {
        val db = this.writableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        val times = Times(arrayListOf())

        if (cursor.moveToFirst()) {
            val idColumnIndex = cursor.getColumnIndex("id")
            val valueColumnIndex = cursor.getColumnIndex("value")

            do {
                times.items.add(TimeItem(cursor.getInt(idColumnIndex), cursor.getString(valueColumnIndex)))
            } while (cursor.moveToNext())
        }

        db.close()
        cursor.close()

        times.items.sortBy {
            it.value
        }

        return times
    }

}