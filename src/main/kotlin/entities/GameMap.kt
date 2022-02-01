package entities

import com.fasterxml.jackson.annotation.JsonCreator
import exceptions.DeserializingException
import util.Direction
import util.getRoomByID
import util.insertNotNull


class GameMap() {
    private class Connection(val roomID: String, val N: String?, val S: String?, val W: String?, val E: String?)
    @Suppress("unused")
    @JsonCreator
    private constructor(rooms: List<Room>, connections: List<Connection>) : this() {
        var insertedFirst = false

        connections.forEach { conn ->
            val room = getRoomByID(conn.roomID, rooms)
                ?: throw DeserializingException("Trying to reference an undeclared roomID: ${conn.roomID}")
            println("Working on ${room.name}")
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


    private val rooms: MutableMap<String, Room> = mutableMapOf()
    private val connections: MutableMap<String, MutableMap<Direction, String>> = mutableMapOf()
    lateinit var entry: Room
        private set

    fun firstRoom(room: Room) {
        if (connections.isNotEmpty()) {
            throw IllegalStateException("This map already has a first room, it's ${entry.name}")
        }
        entry = room
        rooms[room.name] = room
        connections[room.name] = mutableMapOf()
    }

    fun get(room: Room, direction: Direction): Room? = getRoomByName(connections[room.name]?.get(direction)
        ?: throw IllegalStateException("The current map does not have a room called ${room.name}"))

    fun set(room: Room, newRoom: Room, direction: Direction) {
        connections[room.name]?.set(direction, newRoom.name)
        rooms[newRoom.name] = newRoom
        connections[newRoom.name] = mutableMapOf(direction.opposite() to room.name)
    }

    fun getRoomByName(name: String): Room? = rooms[name]
}