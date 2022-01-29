package commands.data

import commands.Command
import entities.people.Player

class InventoryCommand(private val player: Player): Command(false, false) {
    override fun doAction() {
        println("${player.name} looks at his inventory. He has:")
        player.inventory.map { "\t- ${it.name}" }.forEach(::println)
    }
}