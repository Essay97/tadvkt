package commands.actions

import commands.Command
import entities.GameState
import entities.Movable
import entities.people.Player
import util.Direction

class MoveCommand(private val mover: Movable, private val direction: Direction, state: GameState):
    Command(true, true, state) {
    override fun doAction() {
        if (mover.canMove(direction)) {
            mover.move(direction)
            printPosition()
        } else {
            println("This is not possible")
        }
    }

    override fun undoAction() {
        mover.move(direction.opposite())
        printPosition()
    }

    private fun printPosition() {
        if (mover is Player) {
            println("${mover.name} moves in ${mover.currentRoom.name}")
        }
    }


}