package entities.people

import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import util.Input
import util.Output

@JsonDeserialize(`as` = DialogueTemplate::class)
interface Dialogue {
    fun doDialogue(talkerName: String)
}

class DialogueChunk(val message: String, val responses: List<String>)

class DialogueTemplate(@JsonUnwrapped private val chunks: List<DialogueChunk>): Dialogue {
    override fun doDialogue(talkerName: String) {
        val delay = 1200
        chunks.forEach {
            Output.postDelayed(delay, "${talkerName.uppercase()}: ${it.message}")
            it.responses.forEachIndexed { i, resp ->
                println("\t${i + 1}) $resp")
            }
            while (true) {
                try {
                    val input = Input.prompt().toInt() - 1
                    println("YOU: ${it.responses[input]}")
                    break
                } catch (e: Exception) {
                    println("Choose one from the options!")
                }
            }
        }
    }
}