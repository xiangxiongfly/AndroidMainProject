package com.example.activitydemo

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View

const val REQ_CODE = 1

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate()");
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            val name = savedInstanceState.getString("name")
            val age = savedInstanceState.getInt("age")
            val address = savedInstanceState.getString("address")
            log("$name , $age , $address")
        }

        /**
         * App前后台监听
         */
        BaseApplication.INSTANCE.setOnForegroundListener(object :
            BaseApplication.OnForegroundListener {
            override fun onForeground(isForeground: Boolean) {
                log("是否位于前台:$isForeground")
            }
        })

    }

    /**
     * 显式启动
     */
    fun toSecondActivityClick(v: View) {
        startActivity(Intent(this, SecondActivity::class.java))
    }

    /**
     * 显式启动
     */
    fun startActivityForResultClick(v: View) {
        startActivityForResult(Intent(this, SecondActivity::class.java), REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val value: String? = data.getStringExtra("hello")
                    log(value!!)
                }
            }
        }
    }

    /**
     * 隐式启动
     */
    fun startActionClick(v: View) {
        val intent = Intent()
        intent.setAction("com.example.activitydemo.MY_ACTION")
        intent.setDataAndType(Uri.parse("hello://www.123456.com"), "world/*")
        val componentName: ComponentName? = intent.resolveActivity(packageManager)
        if (componentName != null) {
            startActivity(intent)
        } else {
            toast("隐式启动失败")
        }
    }

    fun toOtherActivity(v: View) {
//        val intent = Intent(Intent.ACTION_VIEW)
//        val packageName = "com.example.other"
//        val activityName = "com.example.other.OneActivity" //另一个app要启动的组件的全路径名
//        intent.setClassName(packageName, activityName)
//        startService(intent)
    }

    //------------ Activity生命周期回调 ------
    override fun onRestart() {
        super.onRestart()
        log("MainActivity onRestart()");
    }

    override fun onStart() {
        super.onStart()
        log("MainActivity onStart()");
    }

    override fun onResume() {
        super.onResume()
        log("MainActivity onResume()");
    }

    override fun onPause() {
        super.onPause()
        log("MainActivity onPause()");
    }

    override fun onStop() {
        super.onStop()
        log("MainActivity onStop()");
    }

    override fun onDestroy() {
        super.onDestroy()
        log("MainActivity onDestroy()");
    }
    //------------ Activity生命周期回调 ------


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        log("MainActivity onSaveInstanceState()")
        outState.apply {
            putString("name", "小明")
            putInt("age", 18)
            putString("address", "beijing")
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        log("MainActivity onRestoreInstanceState()")
        val name = savedInstanceState.getString("name")
        val age = savedInstanceState.getInt("age")
        val address = savedInstanceState.getString("address")
        log("$name , $age , $address")
    }
}