package cli

import entities.GameMap
import entities.GameState
import entities.people.Player
import gameLoop
import picocli.CommandLine.Command
import setup.DefaultMapBuilder
import setup.DefaultPlayerBuilder
import java.util.concurrent.Callable

@Command(name = "default")
class AdventureDefault: Callable<Int> {

    private lateinit var player: Player
    private lateinit var map: GameMap

    override fun call(): Int {
        player = DefaultPlayerBuilder().build()
        map = DefaultMapBuilder().build()
        val state = GameState()

        gameLoop(player, map, state)
        return 0
    }
}