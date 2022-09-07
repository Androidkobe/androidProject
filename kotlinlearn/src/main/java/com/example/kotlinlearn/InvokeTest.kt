package com.example.kotlinlearn

/**
 * kotlin 约定  特殊符号 达到 调用方法目的
 * invoke约定，它的作用就是让对象像函数一样调用方法
 */

fun main() {
    val person = Per("geniusmart")
    person()

    val testBoolean = TestBoolean("sundu", 32)
    println(testBoolean.get(0))
    println(testBoolean[1])

    val testBean = TestBean("sundu", 32)
    testBean.invoke()//正常调用
    //约定后简化
    testBean()

//    val testInvoke  = TestInvoke()
//
//    val function1 = object: Function1<Int,Unit> {
//        override fun invoke(p1: Int) {
//         println(p1)
//        }
//    }
////    testInvoke.setSingleListener(function1)
//
//    testInvoke.setSingleListener ({ returnInt ->
//       println("$returnInt")
//    },1)
//
//
// //  invoke 在 DSL 中使用
//    val dependency = DependencyHandler()
////调用invoke
//    dependency.invoke {
//        compile("androidx.core:core-ktx:1.6.0")
//    }
////直接调用
//    dependency.compile("androidx.core:core-ktx:1.6.0")
////带接受者lambda方式
//    dependency{
//        compile("androidx.core:core-ktx:1.6.0")
//    }

    val className = Test1::class.java.name
    println(className)
    try {
        var test = Class.forName(className).newInstance() as Test1
        test.action1 {
            "action1"
        }
        test.action2() { it ->
            "action2 $it"
        }
        test.action3 {
            var result = it()
            "action3 $result"
        }
    } catch (e: Exception) {
        println(e.toString())
    }
}


class Per(val name: String) {
    operator fun invoke() {
        println("my name is $name")
    }
}


data class TestBoolean(val name: String, val age: Int) {
    operator fun get(index: Int): Any {
        return when (index) {
            0 -> name
            1 -> age
            else -> "null"
        }
    }

}

data class TestBean(val name: String, val age: Int) {
    operator fun invoke(): String {
        return "$name - > $age"
    }
}


//invoke 约定与 函数式类型
//定义代码
class TestInvoke {
    //高阶函数类型变量
    private var mSingleListener: ((Int) -> Unit)? = null

    //设置变量
    public fun setSingleListener(listener: ((Int) -> Unit)?, type: Int) {
        this.mSingleListener = listener
    }

    //
    fun testRun() {
        //调用invoke函数
        mSingleListener?.invoke(100)

        //使用invoke约定，省去invoke
        if (mSingleListener != null) {
            mSingleListener!!(100)
        }
    }

}

//注意，这里接口的方法就是invoke
public interface Function1<in P1, out R> : Function<R> {
    /** Invokes the function with the specified argument. */
    public operator fun invoke(p1: P1): R
}

class DependencyHandler {
    //编译库
    fun compile(libString: String) {
        println("add $libString")
    }

    //定义invoke方法
    operator fun invoke(body: DependencyHandler.() -> Unit) {
        body()
    }
}

class Test1 {

    fun action1(block: () -> String) {
        println(block())
    }

    fun action2(block: (v: Int) -> String) {
        println(block(2))
    }

    fun action3(block: (() -> Int) -> String) {
        println(block {
            3
        })
    }
}