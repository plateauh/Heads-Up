package com.najed.headsupv2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "celebs.db", null, 1) {

    private var SQLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        // here we create database table(s)
        db?.execSQL("CREATE TABLE Celebrity (celeb_id INTEGER PRIMARY KEY AUTOINCREMENT, celeb_name TEXT, celeb_taboo1 TEXT, celeb_taboo2 TEXT, celeb_taboo3 TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun addCeleb(celeb: CelebItem): Boolean {
        val contentValues = ContentValues()
        contentValues.put("celeb_name", celeb.name)
        contentValues.put("celeb_taboo1", celeb.taboo1)
        contentValues.put("celeb_taboo2", celeb.taboo2)
        contentValues.put("celeb_taboo3", celeb.taboo3)
        return SQLiteDatabase.insert("Celebrity", null, contentValues) != -1L
    }
}
