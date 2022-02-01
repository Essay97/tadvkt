package entities

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import exceptions.DeserializingException
import setup.GameMapDTO
import util.Direction
import util.getRoomByID
import util.insertNotNull


class GameMap() {

    @Suppress("unused")
    @JsonCreator
    private constructor(rooms: List<Room>, connections: List<GameMapDTO.Connection>) : this() {
        var insertedFirst = false

        connections.forEach { conn ->
            val room = getRoomByID(conn.roomID, rooms)
                ?: throw DeserializingException("Trying to reference an undeclared roomID: ${conn.roomID}")
            val roomN = getRoomByID(conn.N, rooms)
            val roomS = getRoomByID(conn.S, rooms)
            val roomE = getRoomByID(conn.E, rooms)
            val roomW = getRoomByID(conn.W, rooms)

            if (!insertedFirst) {
                insertedFirst = true
                this.firstRoom(room)
            }
            insertNotNull(room, roomN, Direction.N, this)
            insertNotNull(room, roomS, Direction.S, this)
            insertNotNull(room, roomE, Direction.E, this)
            insertNotNull(room, roomW, Direction.W, this)
        }
    }


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
    @Suppress("unused")
    @JsonProperty("rooms")
    private fun deserializeRoot(data: List<Room>) {
        println("THE ROOMS ARE: $data")
    }
}