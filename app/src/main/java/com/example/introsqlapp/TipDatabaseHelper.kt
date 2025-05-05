package com.example.introsqlapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TipDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "tips.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE tips (id INTEGER PRIMARY KEY AUTOINCREMENT, amount REAL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS tips")
        onCreate(db)
    }

    fun insertTip(amount: Double) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("amount", amount)
        }
        db.insert("tips", null, values)
        db.close()
    }

    fun readAllTips(): List<Double> {
        val tips = mutableListOf<Double>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT amount FROM tips", null)
        if (cursor.moveToFirst()) {
            do {
                tips.add(cursor.getDouble(0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return tips
    }
}
