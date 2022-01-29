package commands

import commands.actions.GrabCommand
import commands.data.*
import entities.GameState
import entities.items.GrabbableItem
import entities.people.Player
import util.Direction
import util.Input

abstract class Command(val reversible: Boolean, val counting: Boolean, val state: GameState? = null) {
    fun execute() {
        if (counting) {
            state?.actionsCount = state?.actionsCount?.plus(1) ?: throw IllegalStateException(
                "${this::class.simpleName} supports counting, therefore state cannot be null"
            )
        }
        doAction()
    }

    fun unexecute() {
        if (!reversible) {
            throw UnsupportedOperationException("${this::class.simpleName} does not support undo operation")
        }
        if (counting) {
            state?.actionsCount = state?.actionsCount?.minus(1) ?: throw IllegalStateException(
                "${this::class.simpleName} supports counting, therefore state cannot be null"
            )
        }
        undoAction()
    }

    protected abstract fun doAction()
    protected open fun undoAction() {}

    companion object {
        fun make(input: String, player: Player, state: GameState): Command {
            // grab
            if (input.startsWith("grab")) {
                val arg = Input.extractArgument("grab", input)
                val grabbable = findGrabbableItem(arg, player)
                return GrabCommand(player, state, grabbable)
            }


            return when (input) {
                "inventory" -> InventoryCommand(player)
                "number" -> NumberCommand(state)
                "equip" -> ShowEquipCommand(player)
                "stats" -> StatsCommand(player)
                "where" -> WhereCommand(player)
                "whoami" -> WhoCommand(player)
                "move N" -> MoveCommand(player, Direction.N, state)
                "move S" -> MoveCommand(player, Direction.S, state)
                "move W" -> MoveCommand(player, Direction.W, state)
                "move E" -> MoveCommand(player, Direction.E, state)
                else -> NoCommand()
            }
        }

        private fun findGrabbableItem(match: String, player: Player): GrabbableItem? {
            val item = player.currentRoom.items.find { it.matches(match) && it is GrabbableItem }
            return if (item != null) item as GrabbableItem else null
        }
    }
}