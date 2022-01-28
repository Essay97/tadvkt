package entities

class GameState(var isRunning: Boolean = true, var actionsCount: Int = 0) {
    fun incrementActionsCount() = actionsCount++

    fun decreaseActionsCount() = actionsCount--
}