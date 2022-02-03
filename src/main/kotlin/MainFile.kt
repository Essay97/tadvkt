import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import commands.Command
import commands.Invoker
import entities.GameMap
import entities.GameState
import entities.people.Player
import setup.JacksonMapBuilder
import setup.JacksonPlayerBuilder
import util.Input
import util.Output

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val state = GameState()
            val map = JacksonMapBuilder(YAMLFactory(), "map.yml").build()
            val player = JacksonPlayerBuilder(YAMLFactory(), "player.yml").build()

            gameLoop(player, map, state)

        }
    }
}

fun gameLoop(player: Player, map: GameMap, state: GameState) {
    player.gameMap = map
    println("THE ADVENTURE BEGINS")
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


