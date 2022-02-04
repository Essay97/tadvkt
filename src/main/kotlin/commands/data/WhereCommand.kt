package commands.data

import commands.Command
import entities.people.Player

class WhereCommand(private val player: Player): Command(false, false) {
    override fun doAction() {
        println("${player.name} is in ${player.currentRoom.name}") //WhereHeader
        println(player.currentRoom.description)
    }
}