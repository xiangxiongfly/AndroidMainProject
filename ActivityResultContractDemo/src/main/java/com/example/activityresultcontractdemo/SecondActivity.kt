package com.example.activityresultcontractdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        textView = findViewById(R.id.textView)

        val name = intent.getStringExtra("name")
        textView.text = name
    }

    fun finishClick(view: View) {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra("result", "hello ActivityResult")
        })
        finish()
    }
}