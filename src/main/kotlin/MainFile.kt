import entities.GameMap
import entities.Room
import entities.people.*

fun main() {
    val map = GameMap()

    // Map creation
    // Create test room
    makeTestRoom(map)

    val player = makePlayer(map)
    println(player.currentRoom.name)
    player.currentRoom.npcs.firstOrNull()?.let {
        when (it) {
            is FighterNPC -> {
                it.attack(player)
            }
            is TalkerNPC -> {
                it.talk()
            }
            else -> {
                println("${it.name} is just a random character")
            }
        }
    }

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