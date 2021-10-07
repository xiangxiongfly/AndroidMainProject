package com.example.activityresultcontractdemo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ThirdActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var finishClick: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        textView = findViewById(R.id.textView)
        finishClick = findViewById(R.id.finishClick)

        finishClick.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtras(Bundle().apply {
                    putExtra("name", "小黑")
                    putExtra("age", 28)
                    putExtra("address", "shanghai")
                })
            })
            finish()
        }

        val bundle = intent.extras
        bundle?.apply {
            val name = getString("name")
            val age = getInt("age")
            val address = getString("address")
            textView.text = "name:$name age:$age address:$address"
        }
    }
}