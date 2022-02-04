package setup

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import entities.people.Player
import java.io.File
import java.io.FileNotFoundException


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

