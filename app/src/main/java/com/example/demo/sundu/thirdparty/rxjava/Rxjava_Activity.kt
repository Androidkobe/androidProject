package com.example.demo.sundu.thirdparty.rxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxjava.*

class Rxjava_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava)
        RxJavaButton.setOnClickListener {
            action()
        }
    }

    private fun action() {
        normal()
    }

    private fun normal() {
        Observable.create(object : ObservableOnSubscribe<Int> {
            override fun subscribe(emitter: ObservableEmitter<Int>) {
                emitter.onNext(1)
                emitter.onNext(2)
                emitter.onComplete()
                emitter.onNext(3)
            }
        }).subscribeOn(Schedulers.io()).subscribe(object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                Log.e(
                    "sundu", "normal onSubscribe  + thread name"
                            + Thread.currentThread().name
                )
            }

            override fun onNext(t: Int) {
                Log.e("sundu", "normal onNext $t")
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
                Log.e("sundu", "normal onComplete ")
            }

        })
    }
}