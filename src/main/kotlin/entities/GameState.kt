package entities

class GameState(var isRunning: Boolean = true, actionsCount: Int = 0) {

    var actionsCount: Int = actionsCount
        private set

    fun incrementActionsCount() = actionsCount++

    fun decreaseActionsCount() = actionsCount--
}