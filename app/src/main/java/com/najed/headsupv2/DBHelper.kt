package com.najed.headsupv2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "celebs.db", null, 1) {

    private var sqLiteDatabase: SQLiteDatabase = writableDatabase

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
        return sqLiteDatabase.insert("Celebrity", null, contentValues) != -1L
    }

    fun getAllCeleb(): Celebs {
        var celebs = Celebs()
        val cursor: Cursor = sqLiteDatabase.query("Celebrity", null, null, null, null, null, null)
        if (cursor.count != 0) {
            cursor.moveToFirst()
            do {
                val celebID = cursor.getInt(cursor.getColumnIndexOrThrow("celeb_id"))
                val celebName = cursor.getString(cursor.getColumnIndexOrThrow("celeb_name"))
                val celebTaboo1 = cursor.getString(cursor.getColumnIndexOrThrow("celeb_taboo1"))
                val celebTaboo2 = cursor.getString(cursor.getColumnIndexOrThrow("celeb_taboo2"))
                val celebTaboo3 = cursor.getString(cursor.getColumnIndexOrThrow("celeb_taboo3"))
                celebs.add(CelebItem(celebID, celebName, celebTaboo1, celebTaboo2, celebTaboo3))
            } while (cursor.moveToNext())
        }
        return celebs
    }

    fun updateCeleb(celebID: Int, newCeleb: CelebItem): Boolean {
        val contentValues = ContentValues()
        contentValues.put("celeb_name", newCeleb.name)
        contentValues.put("celeb_taboo1", newCeleb.taboo1)
        contentValues.put("celeb_taboo2", newCeleb.taboo2)
        contentValues.put("celeb_taboo3", newCeleb.taboo3)
        return sqLiteDatabase.update("Celebrity", contentValues, "celeb_id=?", arrayOf(celebID.toString())) != 0
    }

    fun deleteCeleb(celebID: Int): Boolean {
        return sqLiteDatabase.delete("Celebrity", "celeb_id=?", arrayOf(celebID.toString())) != 0
    }
}
