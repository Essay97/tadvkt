package entities.people

import entities.Matchable

interface Talker: Matchable {
    var dialogue: Dialogue
    val name: String

    fun talk() {
        dialogue.doDialogue(name)
    }
}
