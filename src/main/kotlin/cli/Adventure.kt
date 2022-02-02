package cli

import picocli.CommandLine
import picocli.CommandLine.HelpCommand
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@Command(name = "adventure", description = ["A CLI tool to create and play your own text adventure",
    "You can quickstart a default adventure with \"adventure default\""], subcommands = [
        AdventureDefault::class,
        HelpCommand::class
    ]
)
class Adventure: Callable<Int> {

//    @Parameters(index = "0")
//    var player: String = ""

    override fun call(): Int {
        println("Your adventure will start soon!")
        return 0
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            exitProcess(CommandLine(Adventure()).execute(*args))
        }
    }
}