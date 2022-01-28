package entities


class Room(val name: String, override var description: String) : Examinable {

    override val matchers: Set<String> = setOf(name, "this room", "room", "this place", "place")

    // TODO: add items and npcs
}