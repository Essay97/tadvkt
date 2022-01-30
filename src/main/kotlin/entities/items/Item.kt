package entities.items

import entities.Examinable
import entities.people.Player
import util.EquipPart

open class Item(val name: String, override var description: String) : Examinable {
    override val matchers: Set<String> = mutableSetOf(name)
}

abstract class GrabbableItem(name: String, description: String) : Item(name, description) {
    abstract fun use(player: Player)
}

class KeyLockItem(name: String, description: String, private val effect: KeyLockEffect) : GrabbableItem(name, description) {
    override fun use(player: Player) {
        effect.activate(player)
        player.inventory.remove(this)
        println("${player.name} opened the door from ${effect.source.name} to ${effect.destination.name} using ${this.name}")
    }
}

class OneShotItem(name: String, description: String, private val effect: StatsEffect) :
    GrabbableItem(name, description) {
    override fun use(player: Player) {
        effect.activate(player)
        println("${player.name} used ${this.name}")
        player.inventory.remove(this)
    }
}

class EquipItem(name: String, description: String, private val effect: StatsEffect, private val bodyPart: EquipPart) :
    GrabbableItem(name, description) {
    override fun use(player: Player) {
        player.equip[bodyPart]?.revert(player)
        player.equip[bodyPart] = this
        player.inventory.remove(this)
        effect.activate(player)
        println("${player.name} equipped ${this.name}")
    }

    fun revert(player: Player) {
        player.apply {
            hp -= effect.hpDelta
            poisoned -= effect.poison
            stunned -= effect.stun
            burned -= effect.burn
            maxAttack -= effect.attack
        }
        player.inventory.add(this)
        player.equip.remove(bodyPart)
        println("${player.name} took off ${this.name}")
    }
}
