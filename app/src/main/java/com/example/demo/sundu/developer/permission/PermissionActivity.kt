package com.example.demo.sundu.developer.permission

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R

class PermissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        checkHasPermission()
    }

    fun checkHasPermission(){
        var packageInfo = packageManager.getPackageInfo(applicationContext.packageName,PackageManager.GET_PERMISSIONS)
        packageInfo?.let {
            var permissions = it.permissions
            var permissionsRe = it.requestedPermissions
        }
    }
}