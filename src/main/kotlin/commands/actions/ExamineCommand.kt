package commands.actions

import commands.Command
import entities.Examinable
import entities.GameState

class ExamineCommand(state: GameState, private val item: Examinable?):
    Command(false, true, state) {
    override fun doAction() {
        println(item?.description ?: "What do you want to look at?") //NoExaminable
    }
}