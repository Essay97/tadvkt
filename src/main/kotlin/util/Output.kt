package util

import java.util.concurrent.TimeUnit

object Output {
    fun postDelayed(ms: Int, msg: String?) {
        try {
            println(msg)
            TimeUnit.MILLISECONDS.sleep(ms.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}