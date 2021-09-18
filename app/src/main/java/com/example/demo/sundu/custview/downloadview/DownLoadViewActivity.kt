package com.example.demo.sundu.custview.downloadview

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_down_load_view.*

class DownLoadViewActivity : AppCompatActivity() ,View.OnClickListener,Runnable{
    var flikerProgressBar: FlikerProgressBar? = null
    var roundProgressbar: FlikerProgressBar? = null

    var downLoadThread: Thread? = null

    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            flikerProgressBar!!.progress = msg.arg1.toFloat()
            roundProgressbar!!.progress = msg.arg1.toFloat()
            progressbar.setCurrentProgress(msg.arg1)
            if (msg.arg1 == 100) {
                flikerProgressBar!!.finishLoad()
                roundProgressbar!!.finishLoad()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_down_load_view)
        flikerProgressBar = findViewById(R.id.flikerbar) as FlikerProgressBar
        roundProgressbar = findViewById(R.id.round_flikerbar) as FlikerProgressBar
        flikerProgressBar!!.setOnClickListener(this)
        roundProgressbar!!.setOnClickListener(this)
        downLoad()
    }


    fun reLoad(view: View?) {
        downLoadThread!!.interrupt()
        // 重新加载
        flikerProgressBar!!.reset()
        roundProgressbar!!.reset()
        downLoad()
    }

    private fun downLoad() {
        downLoadThread = Thread(this)
        downLoadThread!!.start()
    }



    override fun run() {
        try {
            while (!downLoadThread!!.isInterrupted) {
                var progress = flikerProgressBar!!.progress
                progress += 2f
                Thread.sleep(200)
                val message = handler.obtainMessage()
                message.arg1 = progress.toInt()
                handler.sendMessage(message)
                if (progress == 100f) {
                    break
                }
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun onClick(p0: View?) {
        if (!flikerProgressBar!!.isFinish) {
            flikerProgressBar!!.toggle()
            roundProgressbar!!.toggle()
            if (flikerProgressBar!!.isStop) {
                downLoadThread!!.interrupt()
            } else {
                downLoad()
            }
        }
    }
}