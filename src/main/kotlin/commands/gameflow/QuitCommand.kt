package commands.gameflow

import commands.Command
import entities.GameState

class QuitCommand(state: GameState): Command(false, false, state) {
    override fun doAction() {
        state?.isRunning = false
        println("I hope you had fun, see you soon!")
    }
}