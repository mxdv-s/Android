package ru.itis.kpfu.diyor

fun main() {
    val text1 = "diorbek"
    val text2 = "Diorbek"
    val text3 = "Dir1"
    println(isEmailValid(text1))
    println(isEmailValid(text2))
    println(isEmailValid(text3))

}

private fun isEmailValid(text: String): Boolean {
    val regex = "(?=.*[0-9])(?=.*[A-Z])[A-Za-z0-9]{6,30}"
    return Regex(regex).matches(text)
}