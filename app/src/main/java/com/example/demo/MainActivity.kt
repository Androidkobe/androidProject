package com.example.demo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demo.sundu.developer.DeveloperActivity
import com.example.demo.sundu.jetpack.JetPackActivity
import com.example.demo.sundu.kotlin.KotlinActivity
import com.example.demo.sundu.parcelable.ParcelableActivity
import com.example.demo.sundu.touchevent.TouchEventActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecycleView.setHasFixedSize(true)
        mRecycleView.layoutManager = GridLayoutManager(this,3)
        mRecycleView.adapter = MainAdapter(createData(), itemViewClick)
        myRequetPermission()
    }

    private val itemViewClick: ItemViewClick = object :
        ItemViewClick {
        override fun onItemViewClick(position: Int, view: View) {
            when (position) {
                0 -> startActivity(
                    Intent(
                        this@MainActivity,
                        KotlinActivity::class.java
                    )
                )
                1 -> startActivity(
                    Intent(
                        this@MainActivity,
                        JetPackActivity::class.java
                    )
                )
                2 -> startActivity(
                    Intent(
                        this@MainActivity,
                        DeveloperActivity::class.java
                    )
                )
                3 -> startActivity(
                    Intent(
                        this@MainActivity,
                        ParcelableActivity::class.java
                    )
                )
                4 -> startActivity(
                    Intent(
                        this@MainActivity,
                        TouchEventActivity::class.java
                    )
                )
            }
        }
    }

    private fun createData() : List<String>{
        val stringArray : MutableList<String> = mutableListOf()
        stringArray.add("kotlin")
        stringArray.add("jetPack")
        stringArray.add("Developer")
        stringArray.add("WebView")
        stringArray.add("Parcelabel")
        stringArray.add("TouchEvent")
        return stringArray
    }

    private fun myRequetPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        } else {
            Toast.makeText(this, "您已经申请了权限!", Toast.LENGTH_SHORT).show()
        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 1) {
//            for (i in permissions.indices) {
//                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) { //选择了“始终允许”
//                    Toast.makeText(
//                        this,
//                        "" + "权限" + permissions[i] + "申请成功",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } else {
//                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
//                            this,
//                            permissions[i]
//                        )
//                    ) { //用户选择了禁止不再询问
//                        val builder =
//                            AlertDialog.Builder(this@PermissionActivity)
//                        builder.setTitle("permission")
//                            .setMessage("点击允许才可以使用我们的app哦")
//                            .setPositiveButton(
//                                "去允许"
//                            ) { dialog: DialogInterface?, id: Int ->
//                                if (mDialog != null && mDialog.isShowing()) {
//                                    mDialog.dismiss()
//                                }
//                                val intent =
//                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                                val uri = Uri.fromParts(
//                                    "package",
//                                    packageName,
//                                    null
//                                ) //注意就是"package",不用改成自己的包名
//                                intent.data = uri
//                                startActivityForResult(intent, NOT_NOTICE)
//                            }
//                        mDialog = builder.create()
//                        mDialog.setCanceledOnTouchOutside(false)
//                        mDialog.show()
//                    } else { //选择禁止
//                        val builder =
//                            AlertDialog.Builder(this@PermissionActivity)
//                        builder.setTitle("permission")
//                            .setMessage("点击允许才可以使用我们的app哦")
//                            .setPositiveButton(
//                                "去允许"
//                            ) { dialog: DialogInterface?, id: Int ->
//                                if (alertDialog != null && alertDialog.isShowing()) {
//                                    alertDialog.dismiss()
//                                }
//                                ActivityCompat.requestPermissions(
//                                    this@PermissionActivity,
//                                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                                    1
//                                )
//                            }
//                        alertDialog = builder.create()
//                        alertDialog.setCanceledOnTouchOutside(false)
//                        alertDialog.show()
//                    }
//                }
//            }
//        }
//    }


//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?
//    ) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == NOT_NOTICE) {
//            myRequetPermission() //由于不知道是否选择了允许所以需要再次判断
//        }
//    }
}