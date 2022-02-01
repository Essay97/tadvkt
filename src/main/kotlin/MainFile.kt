import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import entities.GameMap
import entities.Room
import entities.people.*
import util.Direction
import java.io.File

fun main() {

//    // Map creation
//    // Create test room
//    val map = GameMap()
//    val test = makeTestRoom(map)
//
//    val eq1 = EquipItem("sword", "sword poison +3", StatsEffect(poison = 3), EquipPart.RIGHT_HAND)
//    val eq2 = EquipItem("knife", "knife stun +2", StatsEffect(stun = 2), EquipPart.RIGHT_HAND)
//    val eq3 = EquipItem("bracelet", "bracelet attack +6", StatsEffect(attack = 6), EquipPart.WRIST)
//    val shot = OneShotItem("arrow", "arrow attack +2", StatsEffect(attack = 2))
//    val kl = KeyLockItem("key", "opens side room", KeyLockEffect(
//        source = test,
//        destination = Room("side", "side room"),
//        direction = Direction.E
//    )
//    )
//
//    test.items.addAll(listOf(eq1, eq2, eq3, kl, shot))
//
//    val player = makePlayer(map)
//
//    val state = GameState()
//
//    Command.make("grab bracelet", player, state)?.execute()
//    Command.make("use bracelet", player, state)?.execute()
//    Command.make("equip", player, state)?.execute()
//    Command.make("inventory", player, state)?.execute()
//    Command.make("takeoff bracelet", player, state)?.execute()
//    Command.make("equip", player, state)?.execute()
//    Command.make("inventory", player, state)?.execute()

    val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()

    //val player = JacksonPlayerBuilder(YAMLFactory(), "player.yml").build()
    //val map = JacksonMapBuilder(YAMLFactory(), "map.yml").build()

    val anotherMap = mapper.readValue<GameMap>(File("map.yml"))
    println(anotherMap.get(anotherMap.entry, Direction.N)?.description)
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

