package setup

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import entities.GameMap
import java.io.File


abstract class MapBuilder: Builder<GameMap>


class DefaultMapBuilder: MapBuilder() {
    override fun build(): GameMap {
        TODO("Not yet implemented")
    }
}
class PromptMapBuilder: MapBuilder() {
    override fun build(): GameMap {
        TODO("Not yet implemented")
    }
}

class DummyMapBuilder: MapBuilder() {
    override fun build(): GameMap {
        TODO("Not yet implemented")
    }
}

class JacksonMapBuilder(factory: JsonFactory, override val fileName: String): MapBuilder(), JacksonBuilder {
    override val mapper = ObjectMapper(factory).registerKotlinModule()

    override fun build(): GameMap {
        return mapper.readValue(File(fileName))
    }
}

