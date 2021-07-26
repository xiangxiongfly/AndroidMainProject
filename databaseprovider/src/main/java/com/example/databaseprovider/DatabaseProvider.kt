package com.example.databaseprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class DatabaseProvider : ContentProvider() {
    private var dbHelper: MyDatabaseHelper? = null

    private val authority = "com.example.databaseprovider.provider"

    private val bookDir = 0 //访问所有数据
    private val bookItem = 1 //访问指定id的数据
    private val categoryDir = 2
    private val categoryItem = 3

    private val uriMatcher by lazy {
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        uriMatcher.addURI(authority, "book", bookDir)
        uriMatcher.addURI(authority, "book/#", bookItem)
        uriMatcher.addURI(authority, "category", categoryDir)
        uriMatcher.addURI(authority, "category/#", categoryItem)
        uriMatcher
    }

    override fun onCreate(): Boolean {
        return context?.let {
            dbHelper = MyDatabaseHelper(it)
            true
        } ?: false
    }

    /**
     * 查询数据
     */
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor? {
        var cursor: Cursor? = null
        dbHelper?.let {
            val db = it.readableDatabase
            when (uriMatcher.match(uri)) {
                bookDir -> {
                    cursor = db.query(TAB_BOOK,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder)
                }
                bookItem -> {
                    val bookId = uri.pathSegments[1]
                    cursor =
                        db.query(TAB_BOOK,
                            projection,
                            "id=?",
                            arrayOf(bookId),
                            null,
                            null,
                            sortOrder)
                }
                categoryDir -> {
                    cursor = db.query(TAB_CATEGORY,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder)
                }
                categoryItem -> {
                    val categoryId = uri.pathSegments[1]
                    cursor = db.query(TAB_CATEGORY,
                        projection,
                        "id=?",
                        arrayOf(categoryId),
                        null,
                        null,
                        sortOrder)
                }
            }
        }
        return cursor
    }

    /**
     * 添加数据
     */
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        var uriReturn: Uri? = null
        dbHelper?.let {
            val db = it.writableDatabase
            when (uriMatcher.match(uri)) {
                bookDir, bookItem -> {
                    val newBookId = db.insert(TAB_BOOK, null, values)
                    uriReturn = Uri.parse("content://$authority/book/$newBookId")
                }
                categoryDir, categoryItem -> {
                    val newCategoryId = db.insert(TAB_CATEGORY, null, values)
                    uriReturn = Uri.parse("content://$authority/category/$newCategoryId")
                }
            }
        }
        return uriReturn
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int {
        var updateRows = 0
        dbHelper?.let {
            val db = it.writableDatabase
            when (uriMatcher.match(uri)) {
                bookDir -> {
                    updateRows = db.update(TAB_BOOK, values, selection, selectionArgs)
                }
                bookItem -> {
                    val bookId = uri.pathSegments[1]
                    updateRows = db.update(TAB_BOOK, values, "id=?", arrayOf(bookId))
                }
                categoryDir -> {
                    updateRows = db.update(TAB_CATEGORY, values, selection, selectionArgs)
                }
                categoryItem -> {
                    val categoryId = uri.pathSegments[1]
                    updateRows = db.update(TAB_CATEGORY, values, "id=?", arrayOf(categoryId))
                }
            }
        }
        return updateRows
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        var deleteRows = 0
        dbHelper?.let {
            val db = it.writableDatabase
            when (uriMatcher.match(uri)) {
                bookDir -> {
                    deleteRows = db.delete(TAB_BOOK, selection, selectionArgs)
                }
                bookItem -> {
                    val bookId = uri.pathSegments[1]
                    deleteRows = db.delete(TAB_BOOK, "id=?", arrayOf(bookId))
                }
                categoryDir -> {
                    deleteRows = db.delete(TAB_CATEGORY, selection, selectionArgs)
                }
                categoryItem -> {
                    val categoryId = uri.pathSegments[1]
                    deleteRows = db.delete(TAB_CATEGORY, "id=?", arrayOf(categoryId))
                }
            }
        }
        return deleteRows
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            bookDir -> {
                "vnd.android.cursor.dir/vnd.com.example.databaseprovider.provider/book"
            }
            bookItem -> {
                "vnd.android.cursor.item/vnd.com.example.databaseprovider.provider/book"
            }
            categoryDir -> {
                "vnd.android.cursor.dir/vnd.com.example.databaseprovider.provider/category"
            }
            categoryItem -> {
                "vnd.android.cursor.item/vnd.com.example.databaseprovider.provider/category"
            }
            else -> null
        }
    }
}