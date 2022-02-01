package entities.people


abstract class NPC(name: String, description: String) : Character(name, description)

class TalkerNPC(name: String, description: String) : NPC(name, description), Talker {
    override lateinit var dialogue: Dialogue
}

class FighterNPC(name: String, description: String,
                 override var hp: Int, override var maxAttack: Int ) : NPC(name, description), Fighter {
    override var burned = 0
    override var stunned = 0
    override var poisoned = 0
    override var isDefending = false
}

class FighterTalkerNPC(name: String, description: String,
                       override var hp: Int, override var maxAttack: Int) : NPC(name, description), Fighter, Talker {
    override var isDefending = false
    override var stunned = 0
    override var poisoned = 0
    override var burned = 0
    override lateinit var dialogue: Dialogue
}