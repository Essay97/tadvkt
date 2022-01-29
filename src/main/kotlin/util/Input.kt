package util

import java.util.*

object Input {
    private val s = Scanner(System.`in`)

    fun prompt(): String? {
        print("-> ")
        return s.nextLine()
    }

    fun extractArgument(cmd: String, input: String): String{
        return input.substring(cmd.length).trim()
    }
}