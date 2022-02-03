package setup

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import entities.people.Player
import java.io.File

abstract class PlayerBuilder: Builder<Player>

class DefaultPlayerBuilder: PlayerBuilder() {
    override fun build(): Player {
        return Player("John Doe", "John is just a man wandering in a little map. " +
                "He helps you to test the engine :)").apply {
            hp = 20
            maxAttack = 7
        }
    }
}

class PromptPlayerBuilder: PlayerBuilder() {
    override fun build(): Player {
        TODO("Not yet implemented")
    }
}

class DummyPlayerBuilder: PlayerBuilder() {
    override fun build(): Player {
        TODO("Not yet implemented")
    }
}

class JacksonPlayerBuilder(factory: JsonFactory, override val fileName: String): PlayerBuilder(), JacksonBuilder {
    override val mapper = ObjectMapper(factory).registerKotlinModule()

    override fun build(): Player {
        return mapper.readValue(File(fileName))
    }
}

