package setup

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import entities.GameMap
import java.io.File
import java.io.FileNotFoundException


class JacksonMapBuilder(factory: JsonFactory, override val fileName: String): MapBuilder(), JacksonBuilder {
    override val mapper = ObjectMapper(factory).registerKotlinModule()

    override fun build(): GameMap {
        return try {
            mapper.readValue(File(fileName))
        } catch (e: FileNotFoundException) {
            println("Could not find file $fileName, falling back to default player")
            DefaultMapBuilder().build()
        }
    }
}

