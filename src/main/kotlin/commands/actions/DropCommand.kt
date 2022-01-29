package commands.actions

import commands.Command
import entities.GameState
import entities.items.GrabbableItem
import entities.people.Player

class DropCommand(private val player: Player, state: GameState, private val item: GrabbableItem?):
    Command(true, true, state) {
    override fun doAction() {
        if (item != null) {
            player.inventory.remove(item)
            println("${player.name} threw " + item.name + " away.")
            player.currentRoom.items.add(item)
        } else {
            println("${player.name} doesn't own anything like that")
        }
    }

    override fun undoAction() {
        player.inventory.add(item!!)
        println("${player.name} puts ${item.name} back in the inventory.")
    }
}