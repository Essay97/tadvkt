package entities

import entities.items.Item
import entities.people.NPC


class Room(val name: String, override var description: String) : Examinable {

    override val matchers: Set<String> = setOf(name, "this room", "room", "this place", "place")

    val npcs: MutableList<NPC> = mutableListOf()
    val items: MutableSet<Item> = mutableSetOf()
}