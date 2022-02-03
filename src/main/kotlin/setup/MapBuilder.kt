package setup

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import entities.GameMap
import entities.Room
import entities.items.*
import entities.people.*
import util.Direction
import util.EquipPart
import util.Input
import java.io.File
import java.io.FileNotFoundException


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
    private fun room(): Room {
        println("Room name: ")
        val name = Input.prompt()
        println("Room description: ")
        val description = Input.prompt()
        return Room(name, description).apply {
            println("Add items? (Y/N) ")
            var moreItems = Input.prompt()
            while (moreItems in listOf("Y", "y", "yes", "YES")) {
                items.add(item())
                println("More items? (Y/N)")
                moreItems = Input.prompt()
            }
            println("Add NPCs? (Y/N) ")
            var moreNPCs = Input.prompt()
            while (moreNPCs in listOf("Y", "y", "yes", "YES")) {
                npcs.add(npc())
                println("More NPCs? (Y/N)")
                moreNPCs = Input.prompt()
            }
        }
    }

    private fun npc(): NPC {
        println("Character name: ")
        val name = Input.prompt()
        println("Character description: ")
        val description = Input.prompt()
        println("Item type: ")
        println("""
            |1) Fighter
            |2) Talker
            |3) Both
        """.trimMargin())
        var type = Input.prompt().toIntOrNull()
        while (type !in 1..3) {
            println("Choose one of the options")
            type = Input.prompt().toInt()
        }
        return when (type) {
            1 -> FighterNPC(name, description, hp(), maxAttack())
            2 -> TalkerNPC(name, description).apply { dialogue = dialogue() }
            3 -> FighterTalkerNPC(name, description, hp(), maxAttack()).apply { dialogue = dialogue() }
            else -> throw IllegalStateException("NPC type should be constrained between 1 and 3")
        }
    }

    private fun hp(): Int {
        println("Character health points: ")
        return Input.prompt().toInt()
    }

    private fun maxAttack(): Int {
        println("Character maximum damage: ")
        return Input.prompt().toInt()
    }

    private fun dialogue(): Dialogue {
        val chunks = mutableListOf<DialogueChunk>()
        var moreDialogue: String
        do {
            println("Question: ")
            val question = Input.prompt()
            val answers = mutableListOf<String>()
            var moreAnswers: String
            do {
                println("Answer: ")
                answers.add(Input.prompt())
                println("More answers? (Y/N)")
                moreAnswers = Input.prompt()
            } while(moreAnswers in listOf("Y", "y", "yes", "YES"))
            chunks.add(DialogueChunk(question, answers))
            println("More dialogue? (Y/N)")
            moreDialogue = Input.prompt()
        } while (moreDialogue in listOf("Y", "y", "yes", "YES"))
        return DialogueTemplate(chunks)
    }

    private fun item(): Item {
        println("Item name: ")
        val name = Input.prompt()
        println("Item description: ")
        val description = Input.prompt()
        println("Item type: ")
        println("""
            |1) Normal item (not grabbable)
            |2) Equip item
            |3) Key item
            |4) One-shot item
        """.trimMargin())
        var type = Input.prompt().toIntOrNull()
        while (type !in 1..4) {
            println("Choose one of the options")
            type = Input.prompt().toInt()
        }
        return when (type) {
            1 -> Item(name, description)
            2 -> EquipItem(name, description, statsEffect(), bodyPart())
            3 -> KeyLockItem(name, description, keyLockEffect())
            4 -> OneShotItem(name, description, statsEffect())
            else -> throw IllegalStateException("Item type should be constrained between 1 and 4")
        }
    }

    private fun statsEffect(): StatsEffect {
        println("Effect HP change: ")
        val hp = Input.prompt().toInt()
        println("Effect poison change: ")
        val poison = Input.prompt().toInt()
        println("Effect burn change: ")
        val burn = Input.prompt().toInt()
        println("Effect stun change: ")
        val stun = Input.prompt().toInt()
        println("Effect maximum damage change: ")
        val attack = Input.prompt().toInt()

        return StatsEffect(hp, poison, stun, burn, attack)
    }

    private fun bodyPart(): EquipPart {
        println("Body part for item equipping: ")
        var part = Input.prompt()
        while (part !in EquipPart.values().map { it.toString() }) {
            println("Choose one between ${EquipPart.values().joinToString { it.toString() }}")
            part = Input.prompt()
        }
        return EquipPart.valueOf(part)
    }

    private fun keyLockEffect(): KeyLockEffect {
        println("Name of the room from which the key can be used: ")
        val sourceID = Input.prompt()
        println("Direction in which the room will be opened: ")
        println("Room that can be opened: ")
        var direction = Input.prompt()
        while (direction !in EquipPart.values().map { it.toString() }) {
            println("Choose one between ${Direction.values().joinToString { it.toString() }}")
            direction = Input.prompt()
        }
        val room = room()
        return KeyLockEffect(sourceID, room, Direction.valueOf(direction))
    }

    override fun build(): GameMap {
        return GameMap().apply {
            firstRoom(room())
        }
    }
}

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

