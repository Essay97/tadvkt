import commands.Command
import commands.Invoker
import entities.GameMap
import entities.GameState
import entities.people.Player
import setup.PromptMapBuilder
import setup.PromptPlayerBuilder
import util.Input
import util.Output

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val player = PromptPlayerBuilder().build()
            val map = PromptMapBuilder().build()
            val state = GameState()

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


