package entities.items

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import entities.Examinable
import entities.people.Player
import util.EquipPart

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", defaultImpl = Item::class)
@JsonSubTypes(
    JsonSubTypes.Type(value = KeyLockItem::class, name = "KEY_LOCK"),
    JsonSubTypes.Type(value = OneShotItem::class, name = "ONE_SHOT"),
    JsonSubTypes.Type(value = EquipItem::class, name = "EQUIP")
)
open class Item(val name: String, override var description: String) : Examinable {
    override val matchers = mutableSetOf(name)
}

abstract class GrabbableItem(name: String, description: String) : Item(name, description) {
    abstract fun use(player: Player)
}

class KeyLockItem(name: String, description: String, private val effect: KeyLockEffect) : GrabbableItem(name, description) {

    override fun use(player: Player) {
        effect.activate(player)
        player.inventory.remove(this)
        println("${player.name} opened the door from ${effect.sourceID} to ${effect.destination.name} using ${this.name}")
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

class EquipItem(name: String, description: String, private val effect: StatsEffect, val bodyPart: EquipPart) :
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
