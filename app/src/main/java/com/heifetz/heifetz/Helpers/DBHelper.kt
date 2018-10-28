package com.heifetz.heifetz.Helpers

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
        db.execSQL("INSERT INTO $TABLE_NAME (value) VALUES ('10:15'), ('10:35'), ('11:00'), ('11:50'), ('12:15'), ('12:40'), ('13:05'), ('13:30'), ('14:00'), ('14:30'), ('15:00'), ('15:30'), ('16:10'), ('16:35'), ('17:00'), ('17:25'), ('17:50'), ('18:15'), ('18:40'), ('19:05'), ('19:30'), ('19:55'), ('20:20'), ('20:45'), ('21:10'), ('21:25'), ('21:50');")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

     fun getSortedTimes():Times {
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