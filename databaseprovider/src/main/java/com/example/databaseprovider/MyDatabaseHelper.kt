package com.example.databaseprovider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val DB_NAME = "BookStore.db"
private const val VERSION = 2

class MyDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, VERSION) {

    private val createBook = """
        create table $TAB_BOOK (
        id integer primary key autoincrement,
        author text,
        price real,
        pages integer,
        name text,
        category_id integer
        )
    """.trimIndent()

    private val createCategory = """
        create table $TAB_CATEGORY (
        id integer primary key autoincrement,
        category_name text,
        category_code integer
        )
    """.trimIndent()

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createBook)
        db.execSQL(createCategory)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion <= 1) {
            db.execSQL(createCategory)
        }
        if (oldVersion <= 2) {
            db.execSQL("alter table $TAB_BOOK add column category_id integer")
        }
    }
}