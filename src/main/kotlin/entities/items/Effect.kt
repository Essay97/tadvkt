package entities.items

import entities.Room
import entities.people.Player
import util.Direction

interface Effect {
    fun activate(player: Player)
}

class KeyLockEffect(val source: Room, val destination: Room, private val direction: Direction): Effect {
    override fun activate(player: Player) {
        if (player.currentRoom == source) {
            player.gameMap?.set(source, destination, direction)
        } else {
            println("You cannot use this item here.")
        }
    }
}

class StatsEffect(val hpDelta: Int = 0, val poison: Int = 0, val stun: Int = 0, val burn: Int = 0, val attack: Int = 0) :
    Effect {
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