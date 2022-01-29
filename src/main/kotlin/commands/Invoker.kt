package commands

import java.util.*

object Invoker {
    private val stack: Deque<Command> = LinkedList()

    fun invoke(command: Command) {
        command.execute()
        if (command.reversible) {
            stack.addLast(command)
        }
    }

    fun uninvoke() {
        stack.pollLast()?.unexecute() ?: println("You can't go back further")
    }
}