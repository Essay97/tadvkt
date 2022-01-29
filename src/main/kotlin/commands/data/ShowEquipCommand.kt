package commands.data

import commands.Command
import entities.people.Player

class ShowEquipCommand(private val player: Player): Command(false, false) {
    override fun doAction() {
        println("${player.name} has quite an equipment. He has:")
        player.equip.toList().map { "\t- ${it.second.name}" }.forEach(::println)
    }
}