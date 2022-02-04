package setup

import entities.people.Player

class DefaultPlayerBuilder: PlayerBuilder() {
    override fun build(): Player {
        return Player("John Doe", "John is just a man wandering in a little map. " +
                "He helps you to test the engine :)").apply {
            hp = 20
            maxAttack = 7
        }
    }
}
