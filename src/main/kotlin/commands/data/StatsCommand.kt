package commands.data

import commands.Command
import entities.people.Player

class StatsCommand(private val player: Player): Command(false, false) {
    override fun doAction() {
        println("${player.name} is pure power")
        println("HP: ${player.hp}")
        println("STUNNED: ${player.stunned}")
        println("POISONED: ${player.poisoned}")
        println("BURNED: ${player.burned}")
        println("MAX ATTACK: ${player.maxAttack}")
    }
}