package entities.people

import com.fasterxml.jackson.annotation.JsonProperty
import entities.GameMap
import entities.Movable
import entities.Room
import entities.items.EquipItem
import entities.items.GrabbableItem
import entities.items.Item
import exceptions.DeserializingException
import util.Direction
import util.EquipPart

class Player(name: String, description: String) : Character(name, description), Movable, Fighter {
    init {
        matchers.addAll(listOf("me", "player"))
    }

    lateinit var currentRoom: Room
        private set

    var gameMap: GameMap? = null
        set(value) {
            currentRoom = value?.entry ?: throw IllegalArgumentException("gameMap value must be non-null")
            field = value
        }

    @JsonProperty("items")
    val inventory: MutableList<GrabbableItem> = mutableListOf()
    val equip: MutableMap<EquipPart, EquipItem> = mutableMapOf()

    override var hp = 90
    override var maxAttack: Int = 6
    override var isDefending = false
    override var burned = 0
    override var stunned = 0
    override var poisoned = 0

    override fun move(direction: Direction) {
        if (canMove(direction)) {
            currentRoom = gameMap?.get(currentRoom, direction)!!
        } else {
            println("I don't think you can go there")
        }

    }

    override fun canMove(direction: Direction): Boolean {
        return gameMap?.get(currentRoom, direction) != null
    }

    // DESERIALIZATION LOGIC
    @Suppress("unused")
    @JsonProperty("equip")
    private fun equipListToMap(items: List<Item>) {
        if (items.all { it is EquipItem }) {
            items.map { it as EquipItem }.forEach {
                if (equip.containsKey(it.bodyPart)) {
                    throw DeserializingException("The equip of the player is bad formed: body parts can be equipped only once")
                } else {
                    equip[it.bodyPart] = it
                }
            }
        } else {
            throw DeserializingException("The equip of the player is bad formed: all the items must have an equip attribute")
        }
    }
}