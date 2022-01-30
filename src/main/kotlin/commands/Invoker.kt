package commands

import java.util.*

object Invoker {
    private val history: Deque<Command> = LinkedList()

    fun invoke(command: Command) {
        command.execute()
        if (command.reversible) {
            history.addLast(command)
        }
    }

    fun uninvoke() {
        history.pollLast()?.unexecute() ?: println("You can't go back further")
    }
}