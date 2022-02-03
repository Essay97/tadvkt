package setup

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import entities.GameMap
import entities.Room
import entities.items.EquipItem
import entities.items.Item
import entities.items.StatsEffect
import entities.people.DialogueChunk
import entities.people.DialogueTemplate
import entities.people.TalkerNPC
import util.Direction
import util.EquipPart
import java.io.File


abstract class MapBuilder: Builder<GameMap>


class DefaultMapBuilder: MapBuilder() {
    override fun build(): GameMap {
        return GameMap().apply {
            val bedroom = Room("John's bedroom",
                "John is in his bedroom. It's very small, but he has is computer and his favourite baseball bat here." +
                        " In the room there's just one door, to the north, that leads to the kitchen " +
                        "(strange house, I know).").apply {
                items.addAll(listOf(
                    Item("computer", "John's computer. It's turned off (no, you can't turn it on)").apply {
                        matchers.addAll(listOf("pc", "PC"))
                    },
                    EquipItem("baseball bat", "John's favourite baseball bat... come on, we all know, uh?",
                        StatsEffect(attack = 4), EquipPart.RIGHT_HAND).apply {
                        matchers.add("bat")
                    }
                ))
            }
            val kitchen = Room("kitchen",
                "John is in the kitchen. In there, his mum is doing her stuff. John can't see much more in " +
                        "the kitchen, but he knows that his bedroom is to the south").apply {
                npcs.add(TalkerNPC("mum", "John's mum. Keep that baseball bat away from here.").apply {
                    dialogue = DialogueTemplate(listOf(
                        DialogueChunk("Hey, how are you?", listOf("Fine, thanks", "Not that good")),
                        DialogueChunk("Come on this is just a default adventure, it does not have to make sense",
                            listOf("Ok", "Ok"))
                    ))
                })
            }
            firstRoom(bedroom)
            set(bedroom, kitchen, Direction.N)
        }
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

