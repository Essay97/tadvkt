package commands.data

import commands.Command
import entities.GameState

class NumberCommand(state: GameState): Command(false, false, state) {
    override fun doAction() {
        println("You have executed ${state?.actionsCount} commands")
    }
}