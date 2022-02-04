package commands.actions

import commands.Command
import entities.GameState
import entities.items.EquipItem
import entities.people.Player

class TakeOffCommand(private val player: Player, state: GameState, private val item: EquipItem?):
    Command(true, true, state) {
    override fun doAction() {
        item?.revert(player) ?: println("What do you want to take off?") //NoItemTakeOff
    }

    override fun undoAction() {
        item?.use(player) ?: println("Couldn't revert this action") //CouldntRevertAction
    }
}