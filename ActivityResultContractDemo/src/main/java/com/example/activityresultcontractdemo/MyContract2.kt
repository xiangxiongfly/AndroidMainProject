package com.example.activityresultcontractdemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract

class MyContract2 : ActivityResultContract<Bundle, Bundle>() {
    override fun createIntent(context: Context, input: Bundle): Intent {
        return Intent(context, ThirdActivity::class.java).apply {
            putExtras(input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Bundle? {
        val result = intent?.extras
        return if (resultCode == Activity.RESULT_OK) {
            result
        } else {
            null
        }
    }
}