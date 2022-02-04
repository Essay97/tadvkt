package entities.items

import entities.people.Player

class OneShotItem(name: String, description: String, private val effect: StatsEffect) :
    GrabbableItem(name, description) {

    override fun use(player: Player) {
        effect.activate(player)
        println("${player.name} used ${this.name}") //UsedOneShotItem
        player.inventory.remove(this)
    }
}