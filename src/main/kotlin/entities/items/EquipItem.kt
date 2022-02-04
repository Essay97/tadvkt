package entities.items

import entities.people.Player
import util.EquipPart

class EquipItem(name: String, description: String, private val effect: StatsEffect, val bodyPart: EquipPart) :
    GrabbableItem(name, description) {

    override fun use(player: Player) {
        player.equip[bodyPart]?.revert(player)
        player.equip[bodyPart] = this
        player.inventory.remove(this)
        effect.activate(player)
        println("${player.name} equipped ${this.name}") //EquippedItem
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
        println("${player.name} took off ${this.name}") //TookOffItem
    }
}
