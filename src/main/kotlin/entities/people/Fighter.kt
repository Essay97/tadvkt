package entities.people

import entities.Matchable
import util.Output
import java.lang.Integer.max
import kotlin.random.Random

interface Fighter: Matchable {
    var hp: Int
    var maxAttack: Int
    var isDefending: Boolean
    var stunned: Int
    var poisoned: Int
    var burned: Int
    val name: String

    private fun getDamage(): Int = Random(System.currentTimeMillis()).nextInt(maxAttack-1) + 1

    fun attack(enemy: Fighter) {
        val delay = 1200
        Output.postDelayed(delay, "$name attacks!") //FighterAttacks
        if (!enemy.isDefending) {
            val atk = getDamage()
            println("$name rolls a $atk!") //FighterAtkRoll
            enemy.hp -= atk
            val printHP = max(enemy.hp, 0)
            Output.postDelayed(delay, "${enemy.name} has $printHP HP left.") //EnemyHPLeft
        } else {
            println("${enemy.name} defends!") //EnemyDefends
            enemy.isDefending = false
        }
    }
}