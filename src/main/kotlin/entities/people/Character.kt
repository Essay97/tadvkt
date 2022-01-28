package entities.people

import entities.Examinable

abstract class Character protected constructor(val name: String, override var description: String): Examinable {
    override val matchers = mutableSetOf(name)
}