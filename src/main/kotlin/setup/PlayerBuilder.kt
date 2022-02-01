package setup

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import entities.people.Player
import java.io.File


// docs at https://github.com/FasterXML/jackson-module-kotlin/blob/master/README.md


abstract class PlayerBuilder: Builder<Player>


class DefaultPlayerBuilder: PlayerBuilder() {
    override fun build(): Player {
        TODO("Not yet implemented")
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

