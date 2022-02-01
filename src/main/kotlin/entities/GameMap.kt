package entities

import com.fasterxml.jackson.annotation.JsonProperty
import util.Direction

class GameMap {
    private val map: MutableMap<Room, MutableMap<Direction, Room>> = mutableMapOf()
    lateinit var entry: Room
        private set

    fun firstRoom(room: Room) {
        if (map.isNotEmpty()) {
            throw IllegalStateException("This map already has a first room, it's ${entry.name}")
        }
        entry = room
        map[room] = mutableMapOf()
    }

    fun get(room: Room, direction: Direction) = map[room]?.get(direction)

    fun set(room: Room, newRoom: Room, direction: Direction) = map[room]?.set(direction, newRoom)

    fun getRoomByName(name: String): Room? = map.entries.singleOrNull { it.key.matches(name) }?.key

    // DESERIALIZATION LOGIC
    @JsonProperty("rooms")
    private fun deserializeRoot(data: List<Room>) {
        println("THE ROOMS ARE: $data")
    }
}