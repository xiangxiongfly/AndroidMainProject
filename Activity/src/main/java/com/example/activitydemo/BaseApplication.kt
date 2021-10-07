package com.example.activitydemo

import android.app.Activity
import android.app.Application
import android.os.Bundle

class BaseApplication : Application() {


    //Activity数量
    private var activityCount = 0

    //是否在前台
    private var isForeground = false

    companion object {
        lateinit var INSTANCE: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initRegister()
    }

    private fun initRegister() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {
                activityCount++
                if (!isForeground) {
                    setForeground(true)
                    mOnForegroundListener?.onForeground(isForeground())
                }
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
                activityCount--
                if (activityCount == 0) {
                    setForeground(false)
                    mOnForegroundListener?.onForeground(isForeground())
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })
    }

    /**
     * 是否在前台
     * @return Boolean
     */
    fun isForeground() = isForeground

    private fun setForeground(isForeground: Boolean) {
        this.isForeground = isForeground
    }

    interface OnForegroundListener {
        fun onForeground(isForeground: Boolean)
    }

    private var mOnForegroundListener: OnForegroundListener? = null

    fun setOnForegroundListener(onForegroundListener: OnForegroundListener) {
        mOnForegroundListener = onForegroundListener
    }

}