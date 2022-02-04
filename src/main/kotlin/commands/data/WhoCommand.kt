package commands.data

import commands.Command
import entities.people.Player

class WhoCommand(private val player: Player): Command(false, false) {
    override fun doAction() {
        println("${player.name} is the protagonist of our story.") //WhoamiHeader
        println(player.description)
    }
}