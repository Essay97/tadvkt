package entities.items

import entities.people.Player

class KeyLockItem(name: String, description: String, private val effect: KeyLockEffect) : GrabbableItem(name, description) {

    override fun use(player: Player) {
        effect.activate(player)
        player.inventory.remove(this)
        println("${player.name} opened the door from ${effect.sourceID} to ${effect.destination.name} using ${this.name}") //OpenedDoor
    }
}
