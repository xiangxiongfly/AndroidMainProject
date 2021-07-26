package com.example.myapplication

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MyProviderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_provider)
    }

    fun addData(v: View) {
        val uri = Uri.parse("content://com.example.databaseprovider.provider/book")
        var contentValues = ContentValues().apply {
            put("name", "西游记1")
            put("author", "吴承恩")
            put("pages", "1008")
            put("price", "98")
        }
        var contentValues2 = ContentValues().apply {
            put("name", "哈利波特2")
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
        val bookId = 1
        val uri = Uri.parse("content://com.example.databaseprovider.provider/book/$bookId")
        val contentValues = ContentValues().apply {
            put("price", "678")
        }
        contentResolver.update(uri, contentValues, null, null)
    }

    fun deleteData(v: View) {
        val bookId = 1
        val uri = Uri.parse("content://com.example.databaseprovider.provider/book/$bookId")
        contentResolver.delete(uri, null, null)
    }
}