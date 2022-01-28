import entities.GameMap
import entities.Room

fun main() {
    println("Hello World!")
    sayHello("Enrico")
    val gameMap = GameMap()
    println(gameMap.entry?.name)
}

fun sayHello(name: String) = println("Hello $name")