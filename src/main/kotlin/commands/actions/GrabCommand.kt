package commands.actions

import commands.Command
import entities.GameState
import entities.items.GrabbableItem
import entities.people.Player

class GrabCommand(private val player: Player, state: GameState, private val item: GrabbableItem?):
    Command(true, true, state) {
    override fun doAction() {
        if (item != null) {
            player.inventory.add(item)
            player.currentRoom.items.remove(item)
            println("${player.name} puts ${item.name} in inventory.")
        } else {
            println("Oh, you can't grab that")
        }
    }
}