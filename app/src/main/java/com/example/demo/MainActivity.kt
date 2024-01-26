package com.example.demo

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demo.common.ItemViewClick
import com.example.demo.other.yuntask.YunTaskActivity
import com.example.demo.sundu.custview.CustomViewActivity
import com.example.demo.sundu.developer.DeveloperActivity
import com.example.demo.sundu.jetpack.JetPackActivity
import com.example.demo.sundu.kotlin.KotlinActivity
import com.example.demo.sundu.lifecycle.LifecycleActivity
import com.example.demo.sundu.ontouch.TouchActivity
import com.example.demo.sundu.parcelable.ParcelableActivity
import com.example.demo.sundu.recycleview.RecycleViewActivity
import com.example.demo.sundu.thirdparty.ThirdPartyFrameWorkActivity
import com.example.demo.sundu.threadpoll.ThreadPollTest
import com.example.demo.sundu.touchevent.MovieLineActivity
import com.example.demo.sundu.webview.MyWebViewActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val dataMap = HashMap<String, Class<*>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMaxSize()
        setContentView(R.layout.activity_main)
        createData()
        mRecycleView.setHasFixedSize(true)
        mRecycleView.layoutManager = GridLayoutManager(this, 3)
        mRecycleView.adapter = MainAdapter(getData(), itemViewClick)
        myRequetPermission()
    }

    private val itemViewClick: ItemViewClick = object :
        ItemViewClick {
        override fun onItemViewClick(position: Int, view: View) {
            startActivity(Intent(this@MainActivity, dataMap[getData()[position]]))
        }
    }

    override fun onResume() {
        super.onResume()
        var sss = "https://lmg.jj20.com/up/allimg/1113/031920120534/200319120534-7-1200.jpg"
//        GlideApp.with(this).load(sss).into((findViewById<ImageView>(R.id.im)))
        GlideApp.with(this).load(sss).into((findViewById<ImageView>(R.id.im)))
    }

    private fun getData(): List<String> {
        val stringArray: MutableList<String> = mutableListOf()
        dataMap.forEach {
            stringArray.add(it.key)
        }
        return stringArray
    }

    private fun createData() {
        dataMap["kotlin"] = KotlinActivity::class.java
        dataMap["JetPack"] = JetPackActivity::class.java
        dataMap["Developer"] = DeveloperActivity::class.java
        dataMap["WebView"] = MyWebViewActivity::class.java
        dataMap["Parcelable"] = ParcelableActivity::class.java
        dataMap["MovieLine"] = MovieLineActivity::class.java
        dataMap["CustomView"] = CustomViewActivity::class.java
        dataMap["RecycleView"] = RecycleViewActivity::class.java
        dataMap["Touch"] = TouchActivity::class.java
        dataMap["ThirdParty"] = ThirdPartyFrameWorkActivity::class.java
        dataMap["党建云"] = YunTaskActivity::class.java
        dataMap["ThreadPoll"] = ThreadPollTest::class.java
        dataMap["lifecycle"] = LifecycleActivity::class.java
    }


    private fun myRequetPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.VIBRATE
                ),
                1
            )
        } else {
            Toast.makeText(this, "您已经申请了权限!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMaxSize() {
        val maxMemory = (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).memoryClass
        Log.e("sundu", "最大内存 ${maxMemory}")
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