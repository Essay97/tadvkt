package cli

import picocli.CommandLine
import picocli.CommandLine.*
import java.io.PrintWriter
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@Command(name = "adventure", description = ["A CLI tool to create and play your own text adventure",
    "You can quickstart a default adventure with \"adventure default\""], subcommands = [
        AdventureDefault::class,
        HelpCommand::class,
        AdventureCreate::class
    ], mixinStandardHelpOptions = true, synopsisSubcommandLabel = "COMMAND"
)
class Adventure: Callable<Int> {

//    @Parameters(index = "0")
//    var player: String = ""

    override fun call(): Int {

        return 0
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cmd = CommandLine(Adventure())
            if (args.isEmpty()) {
                cmd.usage(cmd.out)
            } else {
                exitProcess(cmd.execute(*args))
            }
        }
    }
}