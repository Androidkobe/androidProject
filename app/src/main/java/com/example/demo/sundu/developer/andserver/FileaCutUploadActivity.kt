package com.example.demo.sundu.developer.andserver

import android.app.Activity
import android.os.Bundle
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_developer_cut_file_upload.upload_start
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets


class FileaCutUploadActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_cut_file_upload)
        upload_start.setOnClickListener {
            start()
        }
    }

    fun start(){
        Thread{
            testDoGet("http:192.168.31.125:8080")
        }.start()
    }

    fun upload(serverUrl:kotlin.String){
        val url = URL(serverUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.doOutput = true
        connection.requestMethod = "POST"
        connection.setRequestProperty("Connection", "Keep-Alive")
        connection.setRequestProperty("Content-Type", "application/octet-stream")
//        connection.setRequestProperty("Chunk-Index", String.valueOf(chunkIndex))
//        connection.setRequestProperty("Total-Size", String.valueOf(totalSize))
//        connection.setRequestProperty("File-Name", fileName)
//        connection.setRequestProperty("Chunk-Size", String.valueOf(chunkSize))

        DataOutputStream(connection.outputStream).use { outputStream ->
//            outputStream.write(chunkData, 0, chunkSize)
            outputStream.flush()
            val responseCode = connection.responseCode
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw Exception("Failed to upload chunk, response code: $responseCode")
            }
        }
    }

    fun testDoGet(url:String) {
        val api = url
        var connection: HttpURLConnection? = null
        var `in`: InputStream? = null
        var reader: BufferedReader? = null
        try {
            //构造一个URL对象
            val url = URL(api)
            //获取URLConnection对象
            connection = url.openConnection() as HttpURLConnection
            //getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，所以在开发中不调用connect()也可以)
            `in` = connection.inputStream
            //通过InputStreamReader将字节流转换成字符串，在通过BufferedReader将字符流转换成自带缓冲流
            reader = BufferedReader(InputStreamReader(`in`, StandardCharsets.UTF_8))
            val sb = StringBuilder()
            var line: kotlin.String? = null
            //按行读取
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            val response = sb.toString()
            println(response)
        } catch (exception: java.lang.Exception) {
            exception.printStackTrace()
        } finally {
            connection?.disconnect()
            if (`in` != null) {
                try {
                    `in`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

}