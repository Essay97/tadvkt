package entities

import com.fasterxml.jackson.annotation.JsonProperty
import entities.items.Item
import entities.people.NPC
import setup.ItemDTO


class Room(val name: String, override var description: String) : Examinable {

    override val matchers: Set<String> = setOf(name, "this room", "room", "this place", "place")

    val npcs: MutableList<NPC> = mutableListOf()
    val items: MutableSet<Item> = mutableSetOf()

    @JsonProperty("items")
    private fun deserializeItems(_items: List<ItemDTO>) {
        items.addAll(_items.map { it.toItem() })
    }

}