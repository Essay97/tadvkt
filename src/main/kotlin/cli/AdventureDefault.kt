package cli

import picocli.CommandLine.Command
import java.util.concurrent.Callable

@Command(name = "default")
class AdventureDefault: Callable<Int> {
    override fun call(): Int {
        println("Here will be the default adventure")
        return 0
    }
}