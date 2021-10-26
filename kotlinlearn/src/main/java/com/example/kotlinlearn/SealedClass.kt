package com.example.kotlinlearn

fun main() {

}

//密封类
fun eval(expr: Expr): Double = when (expr) {
    is Expr.Const -> expr.number
    is Expr.Sum -> eval(expr.e1) + eval(expr.e2)
    is Expr.NotANumber -> Double.NaN
    // 不再需要 `else` 子句，因为我们已经覆盖了所有的情况
}


sealed class Expr {
    data class Const(val number: Double) : Expr()
    data class Sum(val e1: Expr, val e2: Expr) : Expr()
    object NotANumber : Expr()
}

//也可以
//data class Const(val number: Double) : Expr()
//data class Sum(val e1: Expr, val e2: Expr) : Expr()
//object NotANumber : Expr()