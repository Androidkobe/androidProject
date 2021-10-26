package com.example.demo.sundu.thirdparty.rxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.internal.operators.observable.ObservableMap
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
        just()
    }

    private fun normal() {
        Observable.create(object : ObservableOnSubscribe<Int> {
            override fun subscribe(emitter: ObservableEmitter<Int>) {
                emitter.onNext(1)
                emitter.onNext(2)
                emitter.onComplete()
                emitter.onNext(3)
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Int> {
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

    private fun normal2() {
        Observable.create(object : ObservableOnSubscribe<Int> {
            override fun subscribe(emitter: ObservableEmitter<Int>) {
                for (i in 0..10000) {
                    Log.e(
                        "sundu", "thread name - "
                                + Thread.currentThread().name + " 发送 $i"
                    )
                    emitter.onNext(i)
                }
                emitter.onComplete()
            }
        }).subscribeOn(Schedulers.io()).subscribe(object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                Log.e(
                    "sundu", "normal onSubscribe  + thread name"
                            + Thread.currentThread().name
                )
            }

            override fun onNext(t: Int) {
                Log.e(
                    "sundu", "thread name - "
                            + Thread.currentThread().name + " 接收 $t"
                )
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
                Log.e(
                    "sundu", "normal onNext  + thread name"
                            + Thread.currentThread().name
                )
            }

        })
    }

    private fun just() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    Log.e(
                        "sundu", "normal onSubscribe  + thread name"
                                + Thread.currentThread().name
                    )
                }

                override fun onNext(t: Int) {
                    Log.e(
                        "sundu", "thread name - "
                                + Thread.currentThread().name + " 接收 $t"
                    )
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                    Log.e(
                        "sundu", "normal onNext  + thread name"
                                + Thread.currentThread().name
                    )
                }

            })
    }

    private fun map1() {
        ObservableMap<String, String>(object : ObservableSource<String> {
            override fun subscribe(observer: Observer<in String>) {
            }

        }, object : Function<String, String> {
            override fun apply(t: String): String {
                return t
            }
        }).subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: String) {
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
            }

        })
    }

    private fun map2() {
//        Observable.create(object : ObservableOnSubscribe<Int> {
//            override fun subscribe(emitter: ObservableEmitter<Int>) {
//                emitter.onNext(1)
//                emitter.onNext(2)
//                emitter.onComplete()
//                emitter.onNext(3)
//            }
//        }).flatMap {
//            return
//        }
    }

}