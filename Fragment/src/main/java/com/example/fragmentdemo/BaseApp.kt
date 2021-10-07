package com.example.fragmentdemo

import android.app.Application

class BaseApp : Application() {

    companion object {
        lateinit var INSTANCE: BaseApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}