package entities.people

import entities.GameMap
import entities.Movable
import entities.Room
import util.Direction

class Player(name: String, description: String) : Character(name, description), Movable, Fighter {
    init {
        matchers.addAll(listOf("me", "player"))
    }
    var currentRoom: Room? = null
        private set

    var gameMap: GameMap? = null
        set(value) {
            currentRoom = value?.entry
            field = value
        }

    // TODO add inventory, equip

    override var hp = 90
    override var maxAttack: Int = 6
    override var isDefending = false
    override var burned = 0
    override var stunned = 0
    override var poisoned = 0

    override fun move(direction: Direction) {
        currentRoom = gameMap?.get(currentRoom!!, direction)
    }

    override fun canMove(direction: Direction): Boolean {
        return gameMap?.get(currentRoom!!, direction) != null
    }

}