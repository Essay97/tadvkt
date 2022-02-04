package entities

import util.Direction

interface Movable {
    fun move(direction: Direction)
}