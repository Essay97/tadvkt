import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import commands.Command
import commands.Invoker
import entities.GameMap
import entities.GameState
import entities.people.Player
import setup.JacksonMapBuilder
import setup.JacksonPlayerBuilder
import setup.PromptMapBuilder
import setup.PromptPlayerBuilder
import util.Input
import util.Output
import util.TextID
import util.TextProvider

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val player = JacksonPlayerBuilder(YAMLFactory(), "player.yml").build()
            val map = JacksonMapBuilder(YAMLFactory(), "map.yml").build()
            val state = GameState()

            TextProvider.setMapping()
            val text = TextProvider.get(TextID.EnemyHPLeft)
            println(text)

            gameLoop(player, map, state)

        }
    }
}

fun gameLoop(player: Player, map: GameMap, state: GameState) {
    player.gameMap = map
    println("THE ADVENTURE BEGINS") //AdventureBegins
    Output.tutorial()
    println(player.currentRoom.name.uppercase())
    println(player.currentRoom.description)
    while (state.isRunning) {
        if (player.hp <= 0) {
            println("${player.name} is dead")
            break
        } else {
            val input = Input.prompt()
            val command = Command.make(input, player, state)
            if (command == null) {
                Invoker.uninvoke()
            } else {
                Invoker.invoke(command)
            }
        }
    }
}


