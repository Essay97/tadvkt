package util

import commands.NoCommand
import org.w3c.dom.Text

object TextProvider {
    private lateinit var mapping: Map<TextID, String>

    fun setMapping(mapping: Map<TextID, String>? = null) {
        this.mapping = mapping ?: getDefaultMapping()
        if (!TextID.values().all { this.mapping.containsKey(it) }) {
            throw IllegalStateException("Text strings are not completely mapped")
        }
    }

    fun get(id: TextID) = mapping[id]
}

enum class TextID {
    NoUndo, NoCommand, ThrewAway, DoesntOwn, BackInInventory, NoExaminable, NoFighter, FightStarts, PlayerLostFight,
    EnemyLostFight, TiedFight, BurnDamage, PoisonDamage, ChooseFightAction, OptionOutOfBounds, PlayerFightNoItem,
    ChooseItemFight, CantGrab, PlayerMoves, NoItemTakeOff, CouldntRevertAction, NoTalker, NoGrabbableItem,
    InventoryHeader, CommandsCount, EquipHeader, WhereHeader, WhoamiHeader, Greet, KeyLockWrongRoom, EquippedItem,
    TookOffItem, OpenedDoor, UsedOneShotItem, FighterAtkRoll, EnemyDefends, PlayerCantMove, FighterAttacks, EnemyHPLeft,
    AdventureBegins
}

fun getDefaultMapping(): Map<TextID, String> = mapOf(
    TextID.NoUndo to "You can't go back further",
    TextID.NoCommand to "I'm sorry, I don't think I understood...",
    TextID.ThrewAway to "\${player.name} threw \${item.name} away.",
    TextID.DoesntOwn to "\${player.name} doesn't own anything like that",
    TextID.BackInInventory to "\${player.name} puts \${item.name} back in the inventory.",
    TextID.NoExaminable to "What do you want to look at?",
    TextID.NoFighter to "Who do you want to fight with?",
    TextID.FightStarts to "FIGHT STARTS!",
    TextID.PlayerLostFight to "\${player.name} lost!",
    TextID.EnemyLostFight to "\${enemy.name} lost",
    TextID.TiedFight to "It's a tie!",
    TextID.BurnDamage to "The burn is hurting \${fighter.name}! He has \${fighter.hp} HP",
    TextID.PoisonDamage to "The poison is killing \${fighter.name}! He has \${fighter.hp} HP",
    TextID.ChooseFightAction to "What do you want to do?",
    TextID.OptionOutOfBounds to "Choose one from the options!",
    TextID.PlayerFightNoItem to "\${player.name} wanted to use an item but he has no one!",
    TextID.ChooseItemFight to "Which item do you want to use? Choose one of the following: ",
    TextID.CantGrab to "Oh, you can't grab that",
    TextID.PlayerMoves to "\${mover.name} moves in \${mover.currentRoom.name}",
    TextID.NoItemTakeOff to "What do you want to take off?",
    TextID.CouldntRevertAction to "Couldn't revert this action",
    TextID.NoTalker to "Who do you want to talk with?",
    TextID.NoGrabbableItem to "What do you want to use?",
    TextID.InventoryHeader to "\${player.name} looks at his inventory. He has:",
    TextID.CommandsCount to "You have executed \${state?.actionsCount} commands",
    TextID.EquipHeader to "\${player.name} has quite an equipment. He has:",
    TextID.WhereHeader to "\${player.name} is in \${player.currentRoom.name}",
    TextID.WhoamiHeader to "\${player.name} is the protagonist of our story.",
    TextID.Greet to "I hope you had fun, see you soon!",
    TextID.KeyLockWrongRoom to "You cannot use this item here.",
    TextID.EquippedItem to "\${player.name} equipped \${name}",
    TextID.TookOffItem to "\${player.name} took off \${name}",
    TextID.OpenedDoor to "\${player.name} opened the door from \${effect.sourceID} to \${effect.destination.name} using \${name}",
    TextID.UsedOneShotItem to "\${player.name} used \${name}",
    TextID.FighterAtkRoll to "\$name rolls a \$atk!",
    TextID.EnemyDefends to "\${enemy.name} defends!",
    TextID.PlayerCantMove to "I don't think \$name can go there",
    TextID.FighterAttacks to "\$name attacks!",
    TextID.EnemyHPLeft to "\${enemy.name} has \$printHP HP left.",
    TextID.AdventureBegins to "ADVENTURE BEGINS"
)
