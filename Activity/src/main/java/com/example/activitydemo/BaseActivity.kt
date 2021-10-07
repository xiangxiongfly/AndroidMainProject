package com.example.activitydemo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.utils.KeyboardUtils
import java.util.*


open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TopActivity", this.javaClass.name)
        ActivityCollector.addActivity(this)

        //------ 点击空白处收起键盘 方式一 -------
//        val contentView = findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
//        contentView.setOnClickListener {
//            KeyboardUtils.hideKeyboard(currentFocus)
//        }
        //------ 点击空白处收起键盘 方式一 -------
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

    /*
    //------ 点击空白处收起键盘 方式二 -------
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val v = currentFocus
        if (v != null) {
            if (ev.action == MotionEvent.ACTION_DOWN) {
                if (isShouldHideInput(v, ev)) {
                    KeyboardUtils.hideKeyboard(v)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 是否需要隐藏键盘
     */
    private fun isShouldHideInput(v: View, event: MotionEvent): Boolean {
        if (v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationOnScreen(l)
            val left = l[0]
            val top = l[1]
            val bottom: Int = top + v.getHeight()
            val right: Int = left + v.getWidth()
            return !(event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom)
        }
        return false
    }
    //------ 点击空白处收起键盘 方式二 -------
     */

}