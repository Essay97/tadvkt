import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import commands.Command
import entities.GameMap
import entities.GameState
import entities.Room
import entities.people.*
import setup.ItemDTO
import setup.JacksonMapBuilder
import setup.JacksonPlayerBuilder
import java.io.File

fun main() {
    val map = GameMap()

    // Map creation
    // Create test room
    /*val test = makeTestRoom(map)

    val eq1 = EquipItem("sword", "sword poison +3", StatsEffect(poison = 3), EquipPart.RIGHT_HAND)
    val eq2 = EquipItem("knife", "knife stun +2", StatsEffect(stun = 2), EquipPart.RIGHT_HAND)
    val eq3 = EquipItem("bracelet", "bracelet attack +6", StatsEffect(attack = 6), EquipPart.WRIST)
    val shot = OneShotItem("arrow", "arrow attack +2", StatsEffect(attack = 2))
    val kl = KeyLockItem("key", "opens side room", KeyLockEffect(
        source = test,
        destination = Room("side", "side room"),
        direction = Direction.E
    ))

    test.items.addAll(listOf(eq1, eq2, eq3, kl, shot))

    val player = makePlayer(map)

    val state = GameState()

    Command.make("grab bracelet", player, state)?.execute()
    Command.make("use bracelet", player, state)?.execute()
    Command.make("equip", player, state)?.execute()
    Command.make("inventory", player, state)?.execute()
    Command.make("takeoff bracelet", player, state)?.execute()
    Command.make("equip", player, state)?.execute()
    Command.make("inventory", player, state)?.execute()*/

    val playerBuilder = JacksonPlayerBuilder(YAMLFactory(), File("player.yml"))
    val mapBuilder = JacksonMapBuilder(YAMLFactory(), File("map.yml"))

    val gameMap = mapBuilder.build()

    val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
    val item = mapper.readValue<ItemDTO>(File("oneshot.yml")).toItem()
    println(item.name)

    val state = GameState()
    val player = playerBuilder.build()
    println(player.description)
    Command.make("equip", player, state)?.execute()
    Command.make("inventory", player, state)?.execute()
}


fun makePlayer(map: GameMap): Player {
    return Player("Enrico", "This is the player of the game").apply {
        gameMap = map
    }
}

fun makeTestRoom(map: GameMap): Room {
    return Room("Test room", "sandbox room").also { map.firstRoom(it) }
        .apply {
            npcs.addAll(listOf(
                makeTalker(),           // Add talker NPC
                makeFighter(),          // Add fighter NPC
            ))
        }
}

fun makeTalker(): TalkerNPC {
    return TalkerNPC("Talker", "Talker character for testing purposes").apply {
        dialogue = DialogueTemplate(listOf(
            DialogueChunk("Hey!", listOf("How are you?", "Are you ok?")),
            DialogueChunk("Yes, thanks!", listOf("See you later", "Goodbye"))
        ))
    }
}

fun makeFighter(): FighterNPC {
    return FighterNPC("Fighter", "Fighter character for testing purposes", 10, 3)
}

