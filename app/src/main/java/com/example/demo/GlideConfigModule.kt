package com.example.demo

import android.content.Context
import android.text.format.Formatter
import android.util.Log
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream


@GlideModule
class GlideConfigModule : AppGlideModule() {

    lateinit var mContext: Context

    override fun applyOptions(@NonNull context: Context, @NonNull builder: GlideBuilder) {
        super.applyOptions(context, builder)
        mContext = context
        val calculator = MemorySizeCalculator.Builder(context.getApplicationContext()).build()
        val defaultMemoryCacheSize = calculator.memoryCacheSize
        val defaultBitmapPoolSize = calculator.bitmapPoolSize
        val defaultArrayPoolSize = calculator.arrayPoolSizeInBytes
        Log.e(
            "sundu",
            "memory size " + toMb(defaultMemoryCacheSize) + " bitmapPollSize = " + toMb(
                defaultBitmapPoolSize
            )
                    + "arrayPoolSize = " + toMb(defaultArrayPoolSize)
        )
        builder.setLogLevel(Log.VERBOSE)
//        builder.setDefaultRequestOptions(
//                new RequestOptions()
//                        .format(DecodeFormat.PREFER_RGB_565));
//        builder.setMemoryCache(new LruResourceCache(defaultMemoryCacheSize/2));
//        builder.setBitmapPool(new LruBitmapPool(defaultBitmapPoolSize/2));
//        builder.setArrayPool(new LruArrayPool(defaultArrayPoolSize/2));
    }

    private fun toMb(bytes: Int): String? {
        return Formatter.formatFileSize(mContext, bytes.toLong())
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        val client: OkHttpClient = OkHttpClient.Builder().build()
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkhttpU.Factory(client))
    }
}