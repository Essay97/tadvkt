import entities.GameMap
import entities.GameState
import entities.Room
import entities.items.GrabbableItem
import entities.items.Item
import entities.people.*
import util.Direction

fun main() {
    val p = Player("Enrico", "This is the player of the game")
    val state = GameState()
    val map = GameMap()

    // Map creation
    // Create test room
    val test = Room("Test room", "This room is a sandbox test")
    map.firstRoom(test)
    // add talker
    val character = TalkerNPC("Talker", "Talker character for testing purposes")
    character.dialogue = DialogueTemplate(listOf(
        DialogueChunk("Hey!", listOf("How are you?", "Are you ok?"))))
    // add fighter
    val fighter = FighterNPC("Fighter", "Fighter character for testing purposes", 10, 3)
    test.npcs.addAll(listOf(character, fighter))
    // add item
    val item = Item("thing", "test item")
    test.items.add(item)
    // Create side room
    val side = Room("Side room", "Side room to test basic navigation")
    //map.addRoom(side)
    map.set(test, side, Direction.W)

    p.gameMap = map
    println(p.currentRoom?.name)

    p.move(Direction.W)
    println(p.currentRoom?.name)

}