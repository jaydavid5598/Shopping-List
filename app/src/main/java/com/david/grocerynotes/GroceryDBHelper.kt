package com.david.grocerynotes

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.david.grocerynotes.GroceryContract.*

const val DATABASE_NAME = "groceryList.db"
const val DATABASE_VERSION = 1

class GroceryDBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
       val createQuery = "CREATE TABLE " +
               GroceryEntry().TABLE_NAME + " (" +
               GroceryEntry().ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
               GroceryEntry().ITEM + " TEXT NOT NULL, " +
               GroceryEntry().AMOUNT + " INTEGER NOT NULL, " +
               GroceryEntry().TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ")"

        db?.execSQL(createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + GroceryEntry().TABLE_NAME)
        onCreate(db)
    }
}