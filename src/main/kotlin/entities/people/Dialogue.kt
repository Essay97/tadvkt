package entities.people

import util.Input
import util.Output

interface Dialogue {
    fun doDialogue(talkerName: String)
}

class DialogueChunk(val message: String, val responses: List<String>)

class DialogueTemplate(private val chunks: List<DialogueChunk>): Dialogue {
    override fun doDialogue(talkerName: String) {
        val delay = 1200
        chunks.forEach {
            Output.postDelayed(delay, "${talkerName.uppercase()}: ${it.message}")
            it.responses.forEachIndexed { i, resp ->
                println("\t${i + 1}) $resp")
            }
            while (true) {
                try {
                    val input = Input.prompt()?.toInt()?.minus(1)
                    println("YOU: ${it.responses[input ?: throw Exception()]}")
                    break
                } catch (e: Exception) {
                    println("Choose one from the options!")
                }
            }
        }
    }
}