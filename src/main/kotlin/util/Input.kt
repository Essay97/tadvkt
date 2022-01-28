package util

import java.util.*

object Input {
    private val s = Scanner(System.`in`)

    fun prompt(): String? {
        print("-> ")
        return s.nextLine()
    }
}