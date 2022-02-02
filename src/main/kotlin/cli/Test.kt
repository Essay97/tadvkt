package cli


import picocli.CommandLine
import picocli.CommandLine.Option
import picocli.CommandLine.Command
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@Command(name = "test")
class Test: Callable<Int> {

    @Option(names = ["--flag"], description = ["changes the output"])
    var flag = false

    override fun call(): Int {
        if (flag) {
            println("this is the other thing")
        } else {
            println("Responding to command!")
        }
        return 0
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            exitProcess(CommandLine(Test()).execute(*args))
        }
    }
}
