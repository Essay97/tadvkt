package entities.people

class TalkerNPC(name: String, description: String) : NPC(name, description), Talker {
    override var dialogue: Dialogue? = null
}