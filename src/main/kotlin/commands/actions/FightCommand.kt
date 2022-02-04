package commands.actions

import commands.Command
import entities.GameState
import entities.items.OneShotItem
import entities.people.Fighter
import entities.people.Player
import util.Input

class FightCommand(private val player: Player, state: GameState, private val enemy: Fighter?):
    Command(false, true, state) {
    override fun doAction() {
        if (enemy == null) {
            println("Who do you want to fight with?") //NoFighter
        } else {
            println("FIGHT STARTS!") //FightStarts
            while (player.hp > 0 && enemy.hp > 0) {
                applyStatus(player)
                if (player.stunned < 1) {
                    chooseAction(player, enemy)
                }
                if (enemy.hp > 0) {
                    applyStatus(enemy)
                    if (enemy.stunned < 1) {
                        chooseAction(enemy, player)
                    }
                }
            }
            if (player.hp <= 0) {
                println("${player.name} lost!") //PlayerLostFight
            } else if (enemy.hp <= 0) {
                println("${enemy.name} lost!") //EnemyLostFight
            } else {
                println("It's a tie") //TiedFight
            }
        }
    }

    private fun applyStatus(fighter: Fighter) {
        if (fighter.burned > 0) {
            fighter.hp--
            fighter.burned--
            println("The burn is hurting ${fighter.name}! He has ${fighter.hp} HP") //BurnDamage
        }
        if (fighter.poisoned > 0) {
            fighter.hp -= 2
            fighter.poisoned--
            println("The poison is killing ${fighter.name}! He has ${fighter.hp} HP") //PoisonDamage
        }
    }

    private fun chooseAction(fighter: Fighter, target: Fighter) {
        if (fighter is Player) {
            println("What do you want to do?") //ChooseFightAction
            val options = listOf("attack", "defend", "use item")
            options.mapIndexed { i, text -> "\t${i+1} - $text" }.forEach { println(it) }

            do {
                println("Choose one of the options") //OptionOutOfBounds
                val input = Input.prompt().toInt()

                when (input) {
                    1 -> fighter.attack(target)
                    2 -> fighter.isDefending = true
                    3 -> handleItemUsage(fighter)
                }
            } while (input !in 1 until options.size)


        } else {
            // NPC always attacks for now...
            fighter.attack(target)
        }
    }

    private fun handleItemUsage(player: Player) {
        val oneshots = player.inventory.filterIsInstance<OneShotItem>()
        if (oneshots.isEmpty()) {
            println("${player.name} wanted to use an item but he has no one!") //PlayerFightNoItem
        } else {
            var input: Int?
            do {
                println("Which item do you want to use? Choose one of the following: ") //ChooseItemFight
                oneshots.mapIndexed { i, item -> "\t${i + 1} - ${item.name}" }
                    .forEach { println(it) }
                input = Input.prompt().toInt().minus(1)
            } while (input in 1 until player.inventory.size)
            player.inventory[input!!].use(player)
        }
    }
}