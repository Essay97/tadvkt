package commands.actions

import commands.Command
import entities.GameState
import entities.people.Talker

class TalkCommand(private val talker: Talker?, state: GameState): Command(false, true, state) {
    override fun doAction() {
        talker?.talk() ?: println("Who do you want to talk with?")
    }
}