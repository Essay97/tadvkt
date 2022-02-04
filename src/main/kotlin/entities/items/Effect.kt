package entities.items

import com.fasterxml.jackson.annotation.JsonProperty
import entities.Room
import entities.people.Player
import util.Direction

interface Effect {
    fun activate(player: Player)
}

class KeyLockEffect(@JsonProperty("source") val sourceID: String, val destination: Room, private val direction: Direction):
    Effect {
    override fun activate(player: Player) {
        val source = player.gameMap?.getRoomByName(sourceID)
            ?: throw IllegalStateException("The effect of the key/lock item is bad formed: the room $sourceID does not " +
                    "exist in the current map")
        if (player.currentRoom == source) {
            player.gameMap?.set(source, destination, direction)
        } else {
            println("You cannot use this item here.") //KeyLockWrongRoom
        }
    }
}

class StatsEffect(@JsonProperty("hp") val hpDelta: Int = 0, val poison: Int = 0, val stun: Int = 0,
                  val burn: Int = 0, val attack: Int = 0) :
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