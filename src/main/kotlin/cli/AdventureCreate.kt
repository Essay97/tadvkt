package cli

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import entities.GameMap
import entities.GameState
import entities.people.Player
import gameLoop
import picocli.CommandLine.Option
import picocli.CommandLine.Command
import setup.JacksonMapBuilder
import setup.JacksonPlayerBuilder
import setup.MapBuilder
import java.util.concurrent.Callable

@Command(name = "create", description = ["Create a new game by passing a player and a map"])
class AdventureCreate: Callable<Int> {

    @Option(names = ["--player", "-p"], required = true)
    lateinit var playerFile: String
    @Option(names = ["--map", "-m"], required = true)
    lateinit var mapFile: String

    private lateinit var player: Player
    private lateinit var map: GameMap

    override fun call(): Int {

        player = (if (playerFile.endsWith(".yml") or playerFile.endsWith(".YML")
            or playerFile.endsWith(".yaml") or playerFile.endsWith(".YAML")) {
            JacksonPlayerBuilder(YAMLFactory(), playerFile)
        } else if (playerFile.endsWith(".json") or playerFile.endsWith(".JSON")) {
            JacksonPlayerBuilder(JsonFactory(), playerFile)
        } else {
            println("You did not pass a valid file for player")
            return 1
        }).build()

        map = (if (mapFile.endsWith(".yml") or mapFile.endsWith(".YML")
            or mapFile.endsWith(".yaml") or mapFile.endsWith(".YAML")) {
            JacksonMapBuilder(YAMLFactory(), mapFile)
        } else if (mapFile.endsWith(".json") or mapFile.endsWith(".JSON")) {
            JacksonMapBuilder(JsonFactory(), mapFile)
        } else {
            println("You did not pass a valid file for map")
            return 2
        }).build()

        val state = GameState()

        gameLoop(player, map, state)

        return 0
    }
}