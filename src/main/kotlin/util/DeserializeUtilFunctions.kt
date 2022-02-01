package util

import entities.GameMap
import entities.Room
import exceptions.DeserializingException
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy


fun getRoomByID(id: String?, rooms:List<Room>): Room? {
    if (id != null) {
        return rooms.firstOrNull { it.matches(id) }
            ?: throw DeserializingException("Trying to reference an undeclared roomID: $id")
    }
    return null
}

fun insertNotNull(room: Room, newRoom: Room?, direction: Direction, map: GameMap) {
    if (newRoom != null) {
        map.set(room, newRoom, direction)
    }
}
