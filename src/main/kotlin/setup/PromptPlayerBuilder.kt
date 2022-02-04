package setup

import entities.people.Player
import util.Input

class PromptPlayerBuilder: PlayerBuilder() {

    private fun name(): String {
        println("Player name: ")
        return Input.prompt()
    }

    private fun description(): String {
        println("Player description: ")
        return Input.prompt()
    }

    private fun hp(): Int {
        println("Player health points: ")
        return Input.prompt().toInt()
    }

    private fun maxAttack(): Int {
        println("Player maximum damage: ")
        return Input.prompt().toInt()
    }

    override fun build(): Player {
        return Player(name(), description()).apply {
            hp = hp()
            maxAttack = maxAttack()
        }
    }
}
