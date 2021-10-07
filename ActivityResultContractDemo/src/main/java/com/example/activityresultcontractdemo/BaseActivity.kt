package com.example.activityresultcontractdemo

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.utils.KeyboardUtils
import java.util.*


open class BaseActivity : AppCompatActivity() {

    public lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TopActivity", this.javaClass.name)
        mContext = this
        ActivityCollector.addActivity(this)

        val contentView = findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        contentView.setOnClickListener {
            KeyboardUtils.hideKeyboard(currentFocus)
        }
    }

    override fun finish() {
        super.finish()
        //隐藏软键盘，防止内存泄漏
        KeyboardUtils.hideKeyboard(currentFocus)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    object ActivityCollector {
        private val activites = ArrayList<Activity>()

        fun addActivity(activity: Activity) {
            activites.add(activity)
        }

        fun removeActivity(activity: Activity) {
            activites.remove(activity)
        }

        fun finishAll() {
            for (activity in activites) {
                if (!activity.isFinishing) {
                    activity.finish()
                }
            }
            activites.clear()
        }
    }
}