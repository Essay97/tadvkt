package entities.people

class FighterNPC(name: String, description: String,
                 override var hp: Int, override var maxAttack: Int ) : NPC(name, description), Fighter {
    override var burned = 0
    override var stunned = 0
    override var poisoned = 0
    override var isDefending = false
}