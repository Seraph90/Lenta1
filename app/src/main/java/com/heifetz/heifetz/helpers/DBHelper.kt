package com.heifetz.heifetz.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.heifetz.heifetz.enums.Stops
import com.heifetz.heifetz.enums.Stops.*
import com.heifetz.heifetz.models.Time
import com.heifetz.heifetz.models.Times

const val DB_NAME = "times"
const val DB_VERSION = 2

const val TIMES_TABLE_NAME = "times"

const val DROP_TABLE_SQL = "DROP TABLE IF EXISTS $TIMES_TABLE_NAME"

const val CREATE_TIMES_TABLE_SQL =
    "CREATE TABLE $TIMES_TABLE_NAME (id INTEGER PRIMARY KEY AUTOINCREMENT, stop TEXT, value TEXT);"

var INIT_INSERT_TIMES_SQL = """INSERT INTO $TIMES_TABLE_NAME (stop, value) VALUES
        ('${START.name}', '10:25'),
        ('${START.name}', '10:50'),
        ('${START.name}', '11:10'),
        ('${START.name}', '11:30'),
        ('${START.name}', '11:50'),
        ('${START.name}', '12:10'),
        ('${START.name}', '12:35'),
        ('${START.name}', '13:00'),
        ('${START.name}', '13:25'),
        ('${START.name}', '13:45'),
        ('${START.name}', '14:10'),
        ('${START.name}', '14:35'),
        ('${START.name}', '15:00'),
        ('${START.name}', '15:25'),
        ('${START.name}', '16:10'),
        ('${START.name}', '16:35'),
        ('${START.name}', '17:00'),
        ('${START.name}', '17:25'),
        ('${START.name}', '17:50'),
        ('${START.name}', '18:15'),
        ('${START.name}', '18:40'),
        ('${START.name}', '19:05'),
        ('${START.name}', '19:30'),
        ('${START.name}', '19:55'),
        ('${START.name}', '20:20'),
        ('${START.name}', '20:45'),
        ('${START.name}', '21:10'),
        ('${START.name}', '21:25'),
        ('${START.name}', '21:50'),
        ('${START.name}', '22:05'),

        ('${MY.name}', '15:10'),
        ('${MY.name}', '17:38'),
        ('${MY.name}', '19:43');
        """

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    fun init(db: SQLiteDatabase) {
        db.execSQL(DROP_TABLE_SQL)
        db.execSQL(DROP_TABLE_SQL)
        db.execSQL(CREATE_TIMES_TABLE_SQL)
        db.execSQL(INIT_INSERT_TIMES_SQL)
    }

    override fun onCreate(db: SQLiteDatabase) {
        init(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        init(db)
    }

    fun insertTime(time: String, stop: String): Long {
        val contentValues = ContentValues()
        val db = this.writableDatabase

        contentValues.put("value", time)
        contentValues.put("stop", stop)

        val id = db.insert(TIMES_TABLE_NAME, null, contentValues)
        db.close()

        return id
    }

    fun restoreDb() {
        init(this.writableDatabase)
    }

    fun getSortedTimes(): Times {
        val cursor = this.writableDatabase.query(TIMES_TABLE_NAME, null, null, null, null, null, null)
        val times = Times(arrayListOf())

        if (cursor != null && cursor.moveToFirst()) {
            val idColumnIndex = cursor.getColumnIndex("id")
            val valueColumnIndex = cursor.getColumnIndex("value")
            val stopColumnIndex = cursor.getColumnIndex("stop")

            do {
                times.items.add(
                    Time(
                        cursor.getInt(idColumnIndex),
                        Stops.valueOf(cursor.getString(stopColumnIndex)),
                        cursor.getString(valueColumnIndex)
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()

        times.items.sortBy {
            it.value
        }

        return times
    }

}