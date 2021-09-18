package com.example.demo.sundu.jetpack.workmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.demo.R
import kotlinx.android.synthetic.main.jetpack_activity_workmanager.*
import java.util.*
import java.util.concurrent.TimeUnit

class JetPackWorkManagerActivity : AppCompatActivity() {
    private val TAG = "SUNDU-WORK"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.jetpack_activity_workmanager)
        start.setOnClickListener {
            setRepeatingAlarm(context = baseContext, "action sundu", 2, 20 * 1000, false)
        }
//        cancelWorkManager()
//        registerWorkManager()
    }

    fun cancelWorkManager() {
        Log.e("sundu", "取消 设置 work manager")
        WorkManager.getInstance(baseContext).cancelAllWorkByTag("text")
    }

    fun registerWorkManager() {
        Log.e("sundu", "开始 设置 work manager")
        val constraints = androidx.work.Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()
        val request =
            PeriodicWorkRequest.Builder(MyWroker::class.java, 16 * 60 * 1000, TimeUnit.MILLISECONDS)
                .addTag("text")
                .setConstraints(constraints)
                .setInitialDelay(16 * 60 * 1000, TimeUnit.MILLISECONDS)
                .build()
        WorkManager.getInstance(baseContext).enqueue(request)
    }


    /**
     * alarm clock
     */
    private fun setRepeatingAlarm(
        context: Context, cmd: String, requestCode: Int, interval: Long,
        forceIfExist: Boolean
    ) {
        val intent = Intent(context, WakeupService::class.java)
        intent.putExtra(WakeupService.KEY_COMMAND, cmd)
        if (PendingIntent.getService(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_NO_CREATE
            ) != null
            && !forceIfExist
        ) {
            Log.d(TAG, "Exist alarm clock for $cmd, skip")
            return
        }

        var bounceInterval = interval
        val halfInterval = interval / 2
        if (halfInterval > 0) {
            val bounce = Random().nextLong() % halfInterval
            bounceInterval = bounce + interval
        }
        val triggerTime = SystemClock.elapsedRealtime() + bounceInterval
        val alarm = context.getSystemService(ALARM_SERVICE) as AlarmManager

        val sender = PendingIntent.getService(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarm.cancel(sender)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            alarm.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime,sender)
//        }
        alarm.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            triggerTime,
            interval,
            sender
        )
        Log.i(
            TAG,
            "set alarm clock 随机时间 triggerTime :$triggerTime 延迟时间 ：$bounceInterval 间隔时间 : $interval"
        )
    }

    /**
     * alarm clock 固定时间
     */
    private fun setRepeatingAlarmExt(
        context: Context,
        cmd: String,
        requestCode: Int,
        interval: Long
    ) {
        val intent = Intent(context, WakeupService::class.java)
        intent.putExtra(WakeupService.KEY_COMMAND, cmd)
        val triggerTime = System.currentTimeMillis() + interval
        val alarm = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val sender = PendingIntent
            .getService(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarm.cancel(sender)
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, interval, sender)
        Log.i(
            TAG, " set alarm clock  ext 固定时间 当前时间后 source time :$interval 执行"
        )
    }
}