package com.example.activitydemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View

class SecondActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, SecondActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("SecondActivity onCreate()")
        setContentView(R.layout.activity_second)
    }

    fun toThirdActivity(v: View) {
        startActivity(Intent(this, ThirdActivity::class.java))
    }

    fun finishClick(view: View) {
        val intent = Intent().apply {
            putExtra("hello", "world")
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onRestart() {
        super.onRestart()
        log("SecondActivity onRestart()");
    }

    override fun onStart() {
        super.onStart()
        log("SecondActivity onStart()");
    }

    override fun onResume() {
        super.onResume()
        log("SecondActivity onResume()");
    }

    override fun onPause() {
        super.onPause()
        log("SecondActivity onPause()");
    }

    override fun onStop() {
        super.onStop()
        log("SecondActivity onStop()");
    }

    override fun onDestroy() {
        super.onDestroy()
        log("SecondActivity onDestroy()");
    }

}