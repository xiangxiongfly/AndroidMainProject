package com.example.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

object UiUtils {

    fun showDialog(msg: String, context: Context) {
        AlertDialog.Builder(context)
            .setMessage(msg)
            .show()

    }

}