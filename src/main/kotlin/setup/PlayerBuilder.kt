package setup

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import entities.people.Player
import util.Input
import java.io.File
import java.io.FileNotFoundException

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

    private fun name(): String {
        println("Player name: ")
        return Input.prompt()
    }

    private fun description(): String {
        println("Player description: ")
        return Input.prompt()
    }

    private fun hp(): Int {
        println("Player health points: ")
        return Input.prompt().toInt()
    }

    private fun maxAttack(): Int {
        println("Player maximum damage: ")
        return Input.prompt().toInt()
    }

    override fun build(): Player {
        return Player(name(), description()).apply {
            hp = hp()
            maxAttack = maxAttack()
        }
    }
}

class JacksonPlayerBuilder(factory: JsonFactory, override val fileName: String): PlayerBuilder(), JacksonBuilder {
    override val mapper = ObjectMapper(factory).registerKotlinModule()

    override fun build(): Player {
        return try {
            mapper.readValue(File(fileName))
        } catch (e: FileNotFoundException) {
            println("Could not find file $fileName, falling back to default map")
            DefaultPlayerBuilder().build()
        }
    }
}

