package com.example.demo.sundu.developer.sensorxyz

class SenSorData {

    var x = "14"

    var y = "14"

    var z = "14"

}
////自定义双向绑定转换器
////textView只接受String 类型 所以双向绑定时需要转换
//object ConversionSenSorData{
//    @JvmStatic
//    @BindingConversion
//    @BindingAdapter("text", requireAll = false)
//    @InverseMethod("conversionStringToInt")
//    fun conversionIntToString(i:Int):String {
//        return i.toString()
//    }
//
//    @JvmStatic
//    @BindingConversion
//    fun conversionStringToInt(s:String):Int{
//        return s.toInt()
//    }
//}