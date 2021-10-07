package com.example.activitydemo

import android.util.Log
import android.widget.Toast

const val TAG = "Activity"

fun log(msg: String) = Log.e(TAG, msg)


fun toast(msg: String) = Toast.makeText(BaseApplication.INSTANCE, msg, Toast.LENGTH_SHORT).show()
