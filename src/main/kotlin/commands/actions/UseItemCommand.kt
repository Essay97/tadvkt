package commands.actions

import commands.Command
import entities.GameState
import entities.items.GrabbableItem
import entities.people.Player

class UseItemCommand(private val player: Player, state: GameState, private val item: GrabbableItem?):
    Command(false, true, state) {
    override fun doAction() {
        item?.use(player) ?: println("What do you want to use?")
    }
}