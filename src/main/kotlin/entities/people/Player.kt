package entities.people

import entities.GameMap
import entities.Movable
import entities.Room
import entities.items.EquipItem
import entities.items.GrabbableItem
import entities.items.Item
import util.Direction
import util.EquipPart

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

    val inventory: MutableList<GrabbableItem> = mutableListOf()
    val equip: MutableMap<EquipPart, EquipItem> = mutableMapOf()

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

    fun grab(match: String) {
        currentRoom?.items?.firstOrNull { it.matches(match) && it is GrabbableItem}?.let {
            inventory.add(it as GrabbableItem)
            currentRoom!!.items.remove(it)
        } ?: println("Oh, you can't grab that")
    }

}