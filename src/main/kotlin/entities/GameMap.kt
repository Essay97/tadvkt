package entities

import util.Direction

class GameMap {
    val map: MutableMap<Room, MutableMap<Direction, Room>> = mutableMapOf()
    var entry: Room? = null
        private set

    fun addRoom(room: Room) {
        if (map.isEmpty()) {
            entry = room
        }
        map[room] = mutableMapOf()
    }

    fun get(room: Room, direction: Direction) = map[room]?.get(direction)

    fun set(room: Room, newRoom: Room, direction: Direction) = map[room]?.set(direction, newRoom)

    fun getRoomByName(name: String): Room? = map.entries.singleOrNull { it.key.matches(name) }?.key
}