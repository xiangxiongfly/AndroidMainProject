package com.example.databaseprovider

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.contentValuesOf

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: MyDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = MyDatabaseHelper(this)
    }

    fun addData(v: View) {
        val uri = Uri.parse("content://com.example.databaseprovider.provider/book")
        var contentValues = ContentValues().apply {
            put("name", "西游记")
            put("author", "吴承恩")
            put("pages", "1008")
            put("price", "98")
        }
        var contentValues2 = ContentValues().apply {
            put("name", "哈利波特")
            put("author", "罗琳")
            put("pages", "111")
            put("price", "66.77")
        }
        contentResolver.insert(uri, contentValues)
        contentResolver.insert(uri, contentValues2)
    }

    fun queryData(v: View) {
        val uri = Uri.parse("content://com.example.databaseprovider.provider/book")
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val author = cursor.getString(cursor.getColumnIndex("author"))
                val pages = cursor.getString(cursor.getColumnIndex("pages"))
                val price = cursor.getString(cursor.getColumnIndex("price"))
                Log.e("ContentProvider", "$name $author $pages $price")
            }
            cursor.close()
        }
    }

    fun updateData(v: View) {
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put("price", "678")
        }
        db.update(TAB_BOOK, contentValues, "name=?", arrayOf("西游记"))
    }

    fun deleteData(v: View) {
        val db = dbHelper.writableDatabase
        db.delete(TAB_BOOK, "name=?", arrayOf("西游记"))
    }
}