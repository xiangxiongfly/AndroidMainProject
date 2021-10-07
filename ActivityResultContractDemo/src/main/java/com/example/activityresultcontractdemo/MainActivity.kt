package com.example.activityresultcontractdemo

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.utils.UiUtils
import java.io.File


class MainActivity : BaseActivity() {
    private lateinit var textView: TextView

    private val launcher = registerForActivityResult(MyContract()) {
        textView.text = it
    }

    private val launcher2 = registerForActivityResult(MyContract2()) {
        it?.apply {
            val name = getString("name")
            val age = getInt("age")
            val address = getString("address")
            textView.text = "name:$name age:$age address:$address"
        }
    }

    private val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                data?.apply {
                    val name = getStringExtra("name")
                    val age = getIntExtra("age", 0)
                    val address = getStringExtra("address")
                    textView.text = "name:$name age:$age address:$address"
                }
            }
        }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                Toast.makeText(mContext, "权限申请-成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(mContext, "权限申请-失败", Toast.LENGTH_SHORT).show()
            }
        }

    private val multiPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { it ->
            it.forEach {
                if (it.value) {
                    Toast.makeText(mContext, "${it.key} 申请权限-成功", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(mContext, "${it.key} 申请权限-失败", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private val takePicturePreviewLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            Toast.makeText(mContext, "Bitmap大小：${it.byteCount}", Toast.LENGTH_SHORT).show()
        }

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            Toast.makeText(mContext, "拍照是否成功：$it", Toast.LENGTH_SHORT).show()
        }

    private val pickContactLauncher =
        registerForActivityResult(ActivityResultContracts.PickContact()) { it ->
            val cursor: Cursor? = mContext.getContentResolver().query(it, null, null, null, null)

            cursor?.let { it ->
                val builder = StringBuilder()
                while (cursor.moveToNext()) {
                    //联系人id
                    val contactId: String =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    //联系人姓名
                    val contackName: String =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                    builder.append("联系人id：$contactId \n")
                        .append("联系人姓名：$contackName \n")

                    val phones = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null, null
                    )

                    phones?.let { it ->
                        while (it.moveToNext()) {
                            val phoneNumber: String =
                                it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            builder.append("联系人手机号：$phoneNumber \n")
                        }
                        it.close()
                    }
                }
                it.close()

                UiUtils.showDialog(builder.toString(), mContext)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
    }

    fun toActivity1Click(view: View) {
        launcher.launch("hello world")
    }

    fun toActivity2Click(view: View) {
        launcher2.launch(Bundle().apply {
            putString("name", "小白")
            putInt("age", 18)
            putString("address", "beijing")
        })
    }

    /**
     * ActivityResultContracts.StartActivityForResult
     * 启动Activity并返回结果
     */
    fun click1(view: View) {
        activityLauncher.launch(Intent(this, ThirdActivity::class.java).apply {
            putExtra("name", "小花")
            putExtra("age", 38)
            putExtra("address", "guangzhou")
        })
    }

    /**
     * 申请单个权限
     */
    fun click2(view: View) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    /**
     * 申请一组权限
     */
    fun click3(view: View) {
        multiPermissionLauncher.launch(
            arrayOf<String>(
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    /**
     * 拍照
     */
    fun click4(view: View) {
        takePicturePreviewLauncher.launch(null)
    }

    /**
     * 拍照
     */
    fun click5(view: View) {
        val dir = File(cacheDir, "pic")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val picFile = File(dir, "${System.currentTimeMillis()}.jpg")
        takePictureLauncher.launch(
            FileProvider.getUriForFile(
                this@MainActivity,
                "$packageName.provider",
                picFile
            )
        )
    }

    /**
     * 获取联系人
     */
    fun click6(view: View) {
        pickContactLauncher.launch(null)
    }


}