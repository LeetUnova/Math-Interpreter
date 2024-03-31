import java.util.Scanner
import kotlin.collections.ArrayList
import kotlin.math.pow

fun main(args: Array<String>) {
    val scanner: Scanner = Scanner(System.`in`)

    scanner.use {
        while (scanner.hasNextLine()) {
            val line: String = scanner.nextLine()

            if (line == "quit")
                break

            println(interpret(line))
        }
    }
}

fun interpret(expr: String): Double {
    val scanner: Scanner = Scanner(splitOperators(expr))
    val arr: ArrayList<String> = ArrayList()

    scanner.use {
        while (scanner.hasNext())
            arr.add(scanner.next())
    }

    return interpretArr(arr).toDouble()
}

fun interpretArr(expr: ArrayList<String>): String {
    var t: Int = 0

    while (t < expr.size) {
        if (expr[t] != "(") {
            t++
            continue
        }

        val within: ArrayList<String> = ArrayList()
        var parenths: Int = 1
        expr.removeAt(t)

        while (parenths > 0) {
            val token: String = expr.removeAt(t)

            if (token == "(")
                parenths++
            else if (token == ")")
                parenths--

            if (parenths > 0 || token != ")")
                within.add(token)
        }

        val interpreted: String = interpretArr(within)
        expr.add(t++, interpreted)
    }

    t = 0

    while (t < expr.size) {
        if (expr[t] != "^") {
            t++
            continue
        }

        val a: Double = expr.removeAt(t - 1).toDouble()
        val b: Double = expr.removeAt(t).toDouble()
        expr.removeAt(t - 1)

        val c: String = (a.pow(b)).toString()

        expr.add(t - 1, c)
        t--
    }

    t = 0

    while (t < expr.size) {
        if (expr[t] !in arrayOf("*", "/")) {
            t++
            continue
        }

        val a: Double = expr.removeAt(t - 1).toDouble()
        val b: Double = expr.removeAt(t).toDouble()
        val op: String = expr.removeAt(t - 1)

        val c: String =
            if (op == "*") (a * b).toString()
            else (a / b).toString()

        expr.add(t - 1, c)
        t--
    }

    t = 0

    while (t < expr.size) {
        if (expr[t] !in arrayOf("+", "-")) {
            t++
            continue
        }

        val a: Double = expr.removeAt(t - 1).toDouble()
        val b: Double = expr.removeAt(t).toDouble()
        val op: String = expr.removeAt(t - 1)

        val c: String =
            if (op == "+") (a + b).toString()
            else (a - b).toString()

        expr.add(t - 1, c)
        t--
    }

    return expr[0]
}

fun splitOperators(expr: String): String {
    return expr
        .replace("*", " * ")
        .replace("/", " / ")
        .replace("+", " + ")
        .replace("-", " - ")
        .replace("^", " ^ ")
        .replace("(", " ( ")
        .replace(")", " ) ")
        .replace("%", " % ")
}
