package entities.items

import entities.Room
import entities.people.Player
import util.Direction

interface Effect {
    fun activate(player: Player)
}

class KeyLockEffect(private val source: Room, private val destination: Room, private val direction: Direction): Effect {
    override fun activate(player: Player) {
        if (player.currentRoom == source) {
            player.gameMap?.set(source, destination, direction)
        } else {
            println("You cannot use this item here.")
        }
    }
}

class StatsEffect(val hpDelta: Int, val poison: Int, val stun: Int, val burn: Int, val attack: Int): Effect {
    override fun activate(player: Player) {
        player.apply {
            hp += hpDelta
            poisoned += poison
            stunned += stun
            burned += burn
            maxAttack += attack
        }
    }
}