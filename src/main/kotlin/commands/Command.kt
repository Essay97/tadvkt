
package commands

import commands.actions.*
import commands.data.*
import entities.Examinable
import entities.GameState
import entities.items.EquipItem
import entities.items.GrabbableItem
import entities.people.Fighter
import entities.people.Player
import entities.people.Talker
import util.Direction
import util.Input

abstract class Command(open val reversible: Boolean, private val counting: Boolean, val state: GameState? = null) {
    fun execute() {
        if (counting) {
            state?.incrementActionsCount() ?: throw IllegalStateException(
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
            state?.decreaseActionsCount() ?: throw IllegalStateException(
                "${this::class.simpleName} supports counting, therefore state cannot be null"
            )
        }
        undoAction()
    }

    protected abstract fun doAction()
    protected open fun undoAction() {}

    companion object {
        fun make(input: String, player: Player, state: GameState): Command? {
            // grab
            if (input.startsWith("grab")) {
                val arg = Input.extractArgument("grab", input)
                val grabbable = findGrabbableItem(arg, player)
                return GrabCommand(player, state, grabbable)
            }

            // examine
            if (input.startsWith("examine")) {
                val arg = Input.extractArgument("examine", input)
                val examinable = findExaminable(arg, player)
                return ExamineCommand(state, examinable)
            }

            // drop
            if (input.startsWith("drop")) {
                val arg = Input.extractArgument("drop", input)
                val droppable = findInInventory(arg, player)
                return DropCommand(player, state, droppable)
            }

            // use
            if (input.startsWith("use")) {
                val arg = Input.extractArgument("use", input)
                val usable = findInInventory(arg, player)
                return UseItemCommand(player, state, usable)
            }

            // talk
            if (input.startsWith("talk")) {
                val arg = Input.extractArgument("talk", input)
                val talker = findTalker(arg, player)
                return TalkCommand(talker, state)
            }

            // fight
            if (input.startsWith("fight")) {
                val arg = Input.extractArgument("fight", input)
                val fighter = findFighter(arg, player)
                return FightCommand(player, state, fighter)
            }

            // takeoff
            if (input.startsWith("takeoff")) {
                val arg = Input.extractArgument("takeoff", input)
                val equippable = findInEquip(arg, player)
                return TakeOffCommand(player, state, equippable)
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
                "undo" -> null
                else -> NoCommand()
            }
        }

        private fun findGrabbableItem(match: String, player: Player): GrabbableItem? {
            val item = player.currentRoom.items.find { it.matches(match) && it is GrabbableItem }
            return if (item != null) item as GrabbableItem else null
        }

        private fun findExaminable(arg: String, player: Player): Examinable? {
            return (player.inventory + player.equip.map { it.value } + player.currentRoom.items
                    + player.currentRoom.npcs + listOf(player, player.currentRoom))
                .firstOrNull { it.matches(arg) }
        }

        private fun findInInventory(arg: String, player: Player): GrabbableItem? {
            return player.inventory.firstOrNull { it.matches(arg) }
        }

        private fun findTalker(arg: String, player: Player): Talker? {
            val talker = player.currentRoom.npcs.firstOrNull {it.matches(arg) && it is Talker}
            return if (talker != null) talker as Talker else null
        }

        private fun findFighter(arg: String, player: Player): Fighter? {
            val fighter = player.currentRoom.npcs.firstOrNull {it.matches(arg) && it is Fighter }
            return if (fighter != null) fighter as Fighter else null
        }

        private fun findInEquip(arg: String, player: Player): EquipItem? {
            return player.equip.values.firstOrNull { it.matches(arg) }
        }
    }
}
