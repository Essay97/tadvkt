package entities.items

import entities.people.Player

abstract class GrabbableItem(name: String, description: String) : Item(name, description) {
    abstract fun use(player: Player)
}