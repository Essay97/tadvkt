import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import commands.Command
import commands.Invoker
import entities.GameState
import setup.JacksonMapBuilder
import setup.JacksonPlayerBuilder
import util.Input

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val state = GameState()
            val map = JacksonMapBuilder(YAMLFactory(), "map.yml").build()
            val player = JacksonPlayerBuilder(YAMLFactory(), "player.yml").build()
            player.gameMap = map

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
    }
}


