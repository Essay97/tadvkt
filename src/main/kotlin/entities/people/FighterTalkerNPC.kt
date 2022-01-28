package entities.people

class FighterTalkerNPC(name: String, description: String,
                       override var hp: Int, override var maxAttack: Int) : NPC(name, description), Fighter, Talker {
    override var isDefending = false
    override var stunned = 0
    override var poisoned = 0
    override var burned = 0
    override var dialogue: Dialogue? = null
}