package setup

import com.fasterxml.jackson.databind.ObjectMapper
import entities.GameMap
import entities.people.Player

abstract class PlayerBuilder: Builder<Player>

abstract class MapBuilder: Builder<GameMap>

interface JacksonBuilder {
    val mapper: ObjectMapper
    val fileName: String
}

interface Builder<T> {
    fun build(): T
}